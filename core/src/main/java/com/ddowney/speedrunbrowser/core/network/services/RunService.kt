package com.ddowney.speedrunbrowser.core.network.services

import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.responses.Run
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Provides access to the `/runs` route of the speedrun.com API.
 *
 * [More info available here](https://github.com/speedruncomorg/api/blob/master/version1/runs.md)
 */
internal interface RunService {

  @GET("api/v1/runs")
  suspend fun getRuns(
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<Run>

  @GET("api/v1/runs/{id}")
  suspend fun getRun(
    @Path("id") runId: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ObjectRoot<Run>
}