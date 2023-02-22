package com.ddowney.speedrunbrowser.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ddowney.speedrunbrowser.core.model.Genre
import com.ddowney.speedrunbrowser.core.network.responses.GenreResponse

@Entity(tableName = "genre")
internal data class GenreEntity(
  @PrimaryKey val id: String,
  val name: String? = null,
) {

  fun toGenre() = Genre(
    id = id,
    name = name,
  )

  companion object {

    fun toEntity(response: GenreResponse) = GenreEntity(
      id = response.id,
      name = response.name,
    )
  }
}