package com.ddowney.speedrunbrowser.core.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ddowney.speedrunbrowser.core.db.entities.GenreEntity
import com.google.gson.Gson
import dagger.Reusable
import javax.inject.Inject

@ProvidedTypeConverter
@Reusable
internal class Converters @Inject constructor(
  private val gson: Gson,
) {

  @TypeConverter
  fun fromStringList(data: List<String>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toStringList(data: String?): List<String>? = fromJson(data)

  @TypeConverter
  fun fromGenreList(data: List<GenreEntity>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun toGenreList(data: String?): List<GenreEntity>? = fromJson(data)

  @TypeConverter
  fun toStringStringMap(data: Map<String, String>?): String? {
    return gson.toJson(data)
  }

  @TypeConverter
  fun fromStringStringMap(data: String?): Map<String, String>? = fromJson(data)

  private inline fun <reified T> fromJson(data: String?): T? {
    return gson.fromJson(data, T::class.java)
  }
}