package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.BuildConfig.USER_AGENT_HEADER
import com.ddowney.speedrunbrowser.models.Leaderboard
import com.ddowney.speedrunbrowser.models.ListRoot
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Dan on 18/11/2017.
 */
interface CategoriesService {

    @Headers("user-agent: $USER_AGENT_HEADER")
    @GET("api/v1/categories/{id}/records")
    fun getRecordsForCategory(
            @Path("id") id: String,
            @Query("top") top: Int
    ): Observable<ListRoot<Leaderboard>>
}