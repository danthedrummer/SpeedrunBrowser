package com.ddowney.speedrunbrowser.core.network.api

import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.ddowney.speedrunbrowser.core.network.responses.Category
import com.ddowney.speedrunbrowser.core.network.responses.Game
import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.Level
import com.ddowney.speedrunbrowser.core.network.responses.Variable
import com.ddowney.speedrunbrowser.core.network.services.GameService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Fetches [Game] details from the speedrun.com API
 */
public class GameApi @Inject internal constructor(
  private val gameService: GameService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

  /**
   * Gets a page of [Game] entities
   *
   * @param options a map of query params
   * @return a list of [Game] entities
   */
  public suspend fun getGames(
    options: Map<String, String> = emptyMap(),
  ): List<Game> = withContext(ioDispatcher) {
    gameService.getGames(options).data
  }

  /**
   * Gets a specific [Game] entity
   *
   * @param id the ID of the [Game] entity
   * @param options a map of query params
   * @return a [Game] entity
   */
  public suspend fun getGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): Game = withContext(ioDispatcher) {
    gameService.getGame(id, options).data
  }

  /**
   * Gets a list of [Category] entities for a [Game]
   *
   * @param id the ID of the [Game] entity
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
   * Gets a list of [Level] entities for a [Game]
   *
   * @param id the ID of the [Game] entity
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
   * Gets a list of [Variable] entities for a [Game]
   *
   * @param id the ID of the [Game] entity
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
   * Gets a list of derived [Game] entities for a [Game]
   *
   * @param id the ID of the [Game] entity
   * @param options a map of query params
   * @return a list of derived [Game] entities
   */
  public suspend fun getDerivedGamesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Game> = withContext(ioDispatcher) {
    gameService.getDerivedGamesForGame(id, options).data
  }

  /**
   * Gets a list of [Leaderboard] entities for a [Game]
   *
   * @param id the ID of the [Game] entity
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