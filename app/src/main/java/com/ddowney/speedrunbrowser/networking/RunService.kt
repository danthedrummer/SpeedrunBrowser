package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.models.ListRoot
import com.ddowney.speedrunbrowser.models.Run
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RunService {

  @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
  @GET("api/v1/runs")
  fun getRunsForGame(@Query("game") gameId: String): Observable<ListRoot<Run>>

}