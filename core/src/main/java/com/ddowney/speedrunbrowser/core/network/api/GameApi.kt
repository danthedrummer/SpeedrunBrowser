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

public class GameApi @Inject internal constructor(
  private val gameService: GameService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

  public suspend fun getGames(
    options: Map<String, String> = emptyMap(),
  ): List<Game> = withContext(ioDispatcher) {
    gameService.getGames(options).data
  }

  public suspend fun getGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): Game = withContext(ioDispatcher) {
    gameService.getGame(id, options).data
  }

  public suspend fun getCategoriesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Category> = withContext(ioDispatcher) {
    gameService.getCategoriesForGame(id, options).data
  }

  public suspend fun getLevelsForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Level> = withContext(ioDispatcher) {
    gameService.getLevelsForGame(id, options).data
  }

  public suspend fun getVariablesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Variable> = withContext(ioDispatcher) {
    gameService.getVariablesForGame(id, options).data
  }

  public suspend fun getDerivedGamesForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Game> = withContext(ioDispatcher) {
    gameService.getDerivedGamesForGame(id, options).data
  }

  public suspend fun getRecordsForGame(
    id: String,
    options: Map<String, String> = emptyMap(),
  ): List<Leaderboard> = withContext(ioDispatcher) {
    gameService.getRecordsForGame(id, options).data
  }
}