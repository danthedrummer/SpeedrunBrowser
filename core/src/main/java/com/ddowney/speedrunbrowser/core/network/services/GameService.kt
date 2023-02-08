package com.ddowney.speedrunbrowser.core.network.services

import com.ddowney.speedrunbrowser.core.network.responses.Category
import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.network.responses.Leaderboard
import com.ddowney.speedrunbrowser.core.network.responses.Level
import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.responses.Variable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Provides access to the `/games` route of the speedrun.com API.
 *
 * [More info available here](https://github.com/speedruncomorg/api/blob/master/version1/games.md)
 */
internal interface GameService {

  @GET("api/v1/games")
  suspend fun getGames(
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<GameResponse>

  @GET("api/v1/games/{id}")
  suspend fun getGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ObjectRoot<GameResponse>

  @GET("api/v1/games/{id}/categories")
  suspend fun getCategoriesForGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<Category>

  @GET("api/v1/games/{id}/levels")
  suspend fun getLevelsForGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<Level>

  @GET("api/v1/games/{id}/variables")
  suspend fun getVariablesForGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<Variable>

  @GET("api/v1/games/{id}/derived")
  suspend fun getDerivedGamesForGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<GameResponse>

  @GET("api/v1/games/{id}/records")
  suspend fun getRecordsForGame(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<Leaderboard>
}