package com.ddowney.speedrunbrowser.core.serialization

import com.ddowney.speedrunbrowser.core.di.modules.BaseGson
import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.network.responses.GenreResponse
import com.ddowney.speedrunbrowser.core.network.responses.PlatformResponse
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Deserializer responsible for parsing responses containing [GameResponse] data. This is needed
 * because of the embedded types offered by the API which can't be described statically. The
 * fields need to be parsed separately and removed from the data so we can let [gson] do the rest.
 */
internal class GameResponseDeserializer @Inject constructor(
  @BaseGson private val gson: Gson,
) : JsonDeserializer<GameResponse> {

  private val genreDeserializer by lazy { EmbedResponseDeserializer<GenreResponse>(gson) }
  private val platformDeserializer by lazy { EmbedResponseDeserializer<PlatformResponse>(gson) }

  override fun deserialize(
    json: JsonElement?,
    typeOfT: Type?,
    context: JsonDeserializationContext?,
  ): GameResponse {
    val jsonObject = json?.asJsonObject ?: error("Shouldn't be null")

    val embeddedGenres = genreDeserializer.deserialize(jsonObject.remove("genres"))
    val embeddedPlatforms = platformDeserializer.deserialize(jsonObject.remove("platforms"))

    val gameResponse = gson.fromJson(jsonObject.toString(), GameResponse::class.java)
    return gameResponse.copy(
      genres = embeddedGenres,
      platforms = embeddedPlatforms,
    )
  }
}