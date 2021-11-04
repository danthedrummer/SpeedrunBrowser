package com.ddowney.speedrunbrowser.core.network.services

import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Provides access to the `/games` route of the speedrun.com API.
 *
 * [More info available here](https://github.com/speedruncomorg/api/blob/master/version1/leaderboards.md)
 */
internal interface LeaderboardService {

  @GET("api/v1/leaderboards/{gameId}/level/{levelId}/{categoryId}")
  suspend fun getLeaderboard(
    @Path("gameId") gameId: String,
    @Path("levelId") levelId: String,
    @Path("categoryId") categoryId: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ObjectRoot<Leaderboard>

  @GET("api/v1/leaderboards/{gameId}/category/{categoryId}")
  suspend fun getLeaderboard(
    @Path("gameId") gameId: String,
    @Path("categoryId") categoryId: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ObjectRoot<Leaderboard>
}