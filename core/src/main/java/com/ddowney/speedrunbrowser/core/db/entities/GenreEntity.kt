package com.ddowney.speedrunbrowser.core.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ddowney.speedrunbrowser.core.model.Genre

@Entity(
  tableName = "genre",
)
internal data class GenreEntity(
  @PrimaryKey val id: String,
  val name: String? = null,
) {

  fun toGenre() = Genre(
    id = id,
    name = name,
  )
}