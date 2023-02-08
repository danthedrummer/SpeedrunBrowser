package com.ddowney.speedrunbrowser.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddowney.speedrunbrowser.core.db.entities.PlatformEntity

@Dao
internal interface PlatformDao {

  @Query("SELECT * FROM platform")
  suspend fun getAll(): List<PlatformEntity>

  @Query("SELECT * FROM platform WHERE id = :platformId")
  suspend fun getById(platformId: String): PlatformEntity?

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAll(platformEntities: List<PlatformEntity>)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(platformEntity: PlatformEntity)
}