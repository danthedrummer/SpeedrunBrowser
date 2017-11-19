package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.models.LeaderboardModel
import com.ddowney.speedrunbrowser.models.ResponseWrapperM
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Observable

/**
 * Created by Dan on 18/11/2017.
 */
interface CategoriesService {

    @GET("api/v1/categories/{id}/records")
    fun getRecordsForCategory(@Path("id") id : String, @Query("top") top : Int) : Observable<ResponseWrapperM<LeaderboardModel>>
}