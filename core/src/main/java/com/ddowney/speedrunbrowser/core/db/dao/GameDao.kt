package com.ddowney.speedrunbrowser.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddowney.speedrunbrowser.core.db.entities.GameEntity

@Dao
internal interface GameDao {

  @Query("SELECT * FROM game")
  suspend fun getAll(): List<GameEntity>

  @Query("SELECT * FROM game WHERE id = :gameId")
  suspend fun getById(gameId: String): GameEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(games: List<GameEntity>)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(game: GameEntity)
}