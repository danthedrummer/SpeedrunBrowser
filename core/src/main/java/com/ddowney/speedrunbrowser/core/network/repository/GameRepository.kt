package com.ddowney.speedrunbrowser.core.network.repository

import com.ddowney.speedrunbrowser.core.db.SpeedrunBrowserDatabase
import com.ddowney.speedrunbrowser.core.db.entities.GameEntity
import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.ddowney.speedrunbrowser.core.model.Game
import com.ddowney.speedrunbrowser.core.network.responses.Category
import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.Level
import com.ddowney.speedrunbrowser.core.network.responses.Variable
import com.ddowney.speedrunbrowser.core.network.services.GameService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Fetches [GameResponse] details from the speedrun.com API
 */
public class GameRepository @Inject internal constructor(
  private val gameService: GameService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
  database: SpeedrunBrowserDatabase,
) {

  private val gameDao by lazy { database.gameDao() }

  /**
   * Gets a page of [GameResponse] entities
   *
   * @param options a map of query params
   * @return a list of [GameResponse] entities
   */
  public suspend fun getGames(
    options: Map<String, String> = emptyMap(),
  ): List<Game> = withContext(ioDispatcher) {
    val games = gameService.getGames(options).data
      .map(GameEntity::toEntity)
//    gameDao.insertAll(games)
    games.map { it.toGame() }
  }

  /**
   * Gets a specific [GameResponse] entity
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a [GameResponse] entity
   */
  public suspend fun getGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): Game = withContext(ioDispatcher) {
    val localGame = gameDao.getById(id)
    if (localGame != null) {
      return@withContext localGame.toGame()
    }

    val remoteGame = gameService.getGame(id, options).data
    val entity = GameEntity.toEntity(remoteGame)
//    gameDao.insert(entity)
    entity.toGame()
  }

  /**
   * Gets a list of [Category] entities for a [GameResponse]
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a list of [Category] entities
   */
  public suspend fun getCategoriesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Category> = withContext(ioDispatcher) {
    gameService.getCategoriesForGame(id, options).data
  }

  /**
   * Gets a list of [Level] entities for a [GameResponse]
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a list of [Level] entities
   */
  public suspend fun getLevelsForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Level> = withContext(ioDispatcher) {
    gameService.getLevelsForGame(id, options).data
  }

  /**
   * Gets a list of [Variable] entities for a [GameResponse]
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a list of [Variable] entities
   */
  public suspend fun getVariablesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Variable> = withContext(ioDispatcher) {
    gameService.getVariablesForGame(id, options).data
  }

  /**
   * Gets a list of derived [GameResponse] entities for a [GameResponse]
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a list of derived [GameResponse] entities
   */
  public suspend fun getDerivedGamesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<GameResponse> = withContext(ioDispatcher) {
    gameService.getDerivedGamesForGame(id, options).data
  }

  /**
   * Gets a list of [Leaderboard] entities for a [GameResponse]
   *
   * @param id the ID of the [GameResponse] entity
   * @param options a map of query params
   * @return a list of [Leaderboard] entities
   */
  public suspend fun getRecordsForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Leaderboard> = withContext(ioDispatcher) {
    gameService.getRecordsForGame(id, options).data
  }
}