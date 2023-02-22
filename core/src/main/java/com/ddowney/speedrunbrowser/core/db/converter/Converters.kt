package com.ddowney.speedrunbrowser.core.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ddowney.speedrunbrowser.core.db.entities.GenreEntity
import com.ddowney.speedrunbrowser.core.db.entities.PlatformEntity
import com.ddowney.speedrunbrowser.core.di.modules.BaseGson
import com.google.gson.Gson
import dagger.Reusable
import javax.inject.Inject

@ProvidedTypeConverter
@Reusable
internal class Converters @Inject constructor(
  @BaseGson private val gson: Gson,
) {

  // region List<String>
  @TypeConverter
  fun fromStringList(data: List<String>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toStringList(data: String?): List<String>? = fromJson(data)
  // endregion

  // region List<GenreEntity>
  @TypeConverter
  fun fromGenreList(data: List<GenreEntity>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toGenreList(data: String?): List<GenreEntity>? = fromJson(data)
  // endregion

  // region List<PlatformEntity>
  @TypeConverter
  fun fromPlatformList(data: List<PlatformEntity>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toPlatformList(data: String?): List<PlatformEntity>? = fromJson(data)
  // endregion

  // region Map<String, String>
  @TypeConverter
  fun fromStringStringMap(data: Map<String, String>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toStringStringMap(data: String?): Map<String, String>? = fromJson(data)
  // endregion

  private inline fun <reified T> fromJson(data: String?): T? {
    return gson.fromJson(data, T::class.java)
  }
}