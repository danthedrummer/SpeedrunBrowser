package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.models.Leaderboard
import com.ddowney.speedrunbrowser.models.ListRoot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesService {

  @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
  @GET("api/v1/categories/{id}/records")
  fun getRecordsForCategory(
    @Path("id") id: String,
    @Query("top") top: Int,
  ): Observable<ListRoot<Leaderboard>>

}