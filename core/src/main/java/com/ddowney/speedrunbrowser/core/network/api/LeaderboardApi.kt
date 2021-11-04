package com.ddowney.speedrunbrowser.core.network.api

import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.ddowney.speedrunbrowser.core.network.responses.Game
import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.Level
import com.ddowney.speedrunbrowser.core.network.services.LeaderboardService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Fetches [Leaderboard] details from the speedrun.com API
 */
public class LeaderboardApi @Inject internal constructor(
  private val leaderboardService: LeaderboardService,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

  /**
   * Gets a [Leaderboard] entity for a [Game] [Category]
   *
   * @param gameId the ID of the [Game] entity
   * @param categoryId the ID of the [Category]
   * @param options a map of query params
   * @return a list of [Level] entities
   */
  public suspend fun getLeaderboard(
    gameId: String,
    categoryId: String,
    options: Map<String, String> = emptyMap(),
  ): Leaderboard = withContext(ioDispatcher) {
    leaderboardService.getLeaderboard(gameId, categoryId, options).data
  }

  /**
   * Gets a [Leaderboard] entity for a [Level] [Category]
   *
   * @param gameId the ID of the [Game] entity
   * @param levelId the ID of the [Level]
   * @param categoryId the ID of the [Category]
   * @param options a map of query params
   * @return a list of [Level] entities
   */
  public suspend fun getLeaderboard(
    gameId: String,
    levelId: String,
    categoryId: String,
    options: Map<String, String> = emptyMap(),
  ): Leaderboard = withContext(ioDispatcher) {
    leaderboardService.getLeaderboard(gameId, levelId, categoryId, options).data
  }
}