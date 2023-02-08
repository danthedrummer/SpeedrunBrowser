package com.ddowney.speedrunbrowser.core.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ddowney.speedrunbrowser.core.model.Platform
import com.ddowney.speedrunbrowser.core.network.responses.PlatformResponse

@Entity(tableName = "platform")
internal data class PlatformEntity(
  @PrimaryKey val id: String,
  val name: String,
) {

  fun toPlatform(): Platform = Platform(
    id = id,
    name = name,
  )

  companion object {
    fun toEntity(response: PlatformResponse): PlatformEntity = PlatformEntity(
      id = response.id,
      name = response.name,
    )
  }
}