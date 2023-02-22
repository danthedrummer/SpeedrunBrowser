package com.ddowney.speedrunbrowser.core.serialization

import com.ddowney.speedrunbrowser.core.di.modules.BaseGson
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javax.inject.Inject

/**
 * Deserializer responsible for parsing [Embed] fields in responses. We need a reference of the
 * [innerClass] so we know which complex type to deserialize when the property is embedded.
 */
internal class EmbedResponseDeserializer<T> @Inject constructor(
  @BaseGson private val gson: Gson,
  private val innerClass: Class<T>,
) {

  fun deserialize(json: JsonElement?): Embed<T> = when {
    json == null -> error("Shouldn't be null")
    json.isJsonObject -> fromObject(json.asJsonObject)
    json.isJsonArray -> fromArray(json.asJsonArray)
    json.isJsonNull -> Embed.Raw(data = emptyList()) // return an empty raw type for non-nullability
    else -> error("No idea what type this is - $json")
  }

  /**
   * Deserializes the nested "data" property into an [Embed.Populated] complex type
   * described by [innerClass].
   */
  private fun fromObject(
    jsonObject: JsonObject,
  ): Embed.Populated<T> = Embed.Populated(
    data = buildList {
      jsonObject.get("data").asJsonArray.iterator().forEach { item ->
        gson.fromJson(item.toString(), innerClass).let(::add)
      }
    }
  )

  /**
   * Deserializes the nested "data" property into [Embed.Raw].
   */
  private fun fromArray(
    jsonArray: JsonArray,
  ): Embed.Raw<T> = Embed.Raw(
    data = buildList {
      jsonArray.iterator().forEach { item ->
        add(item.asString)
      }
    }
  )

  companion object {

    /**
     * Shorthand function for creating an instance of [EmbedResponseDeserializer] inlining the
     * type to retrieve the [Class] for the constructor.
     */
    inline operator fun <reified T> invoke(gson: Gson): EmbedResponseDeserializer<T> =
      EmbedResponseDeserializer(gson, T::class.java)
  }
}