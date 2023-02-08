package com.ddowney.speedrunbrowser.core.network.services

import com.ddowney.speedrunbrowser.core.network.responses.ListRoot
import com.ddowney.speedrunbrowser.core.network.responses.ObjectRoot
import com.ddowney.speedrunbrowser.core.network.responses.PlatformResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface PlatformService {

  @GET("api/v1/platforms")
  suspend fun getPlatforms(
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ListRoot<PlatformResponse>

  @GET("api/v1/platforms/{id}")
  suspend fun getPlatformById(
    @Path("id") id: String,
    @QueryMap options: Map<String, String> = emptyMap(),
  ): ObjectRoot<PlatformResponse>
}