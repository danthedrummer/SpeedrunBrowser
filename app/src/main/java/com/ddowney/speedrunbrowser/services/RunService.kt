package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.models.ResponseWrapperM
import com.ddowney.speedrunbrowser.models.RunModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dan on 01/11/2017.
 */
interface RunService {

    @GET("api/v1/runs")
    fun getRunsForGame(@Query("game") gameId : String) : Observable<ResponseWrapperM<RunModel>>
}