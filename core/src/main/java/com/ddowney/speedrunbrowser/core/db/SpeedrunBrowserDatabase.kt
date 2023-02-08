package com.ddowney.speedrunbrowser.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddowney.speedrunbrowser.core.db.converter.Converters
import com.ddowney.speedrunbrowser.core.db.dao.GameDao
import com.ddowney.speedrunbrowser.core.db.dao.PlatformDao
import com.ddowney.speedrunbrowser.core.db.entities.GameEntity
import com.ddowney.speedrunbrowser.core.db.entities.GenreEntity
import com.ddowney.speedrunbrowser.core.db.entities.PlatformEntity

@Database(
  entities = [
    GameEntity::class,
    PlatformEntity::class,
    GenreEntity::class,
  ],
  version = 1,
)
@TypeConverters(
  Converters::class,
)
internal abstract class SpeedrunBrowserDatabase : RoomDatabase() {

  abstract fun gameDao(): GameDao

  abstract fun platformDao(): PlatformDao
}