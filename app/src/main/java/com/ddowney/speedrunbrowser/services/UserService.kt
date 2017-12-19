package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.models.ResponseWrapperS
import com.ddowney.speedrunbrowser.models.UserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Dan on 19/12/2017.
 */
interface UserService {

//    @GET("api/v1/categories/{id}/records")
//    fun getRecordsForCategory(@Path("id") id : String, @Query("top") top : Int) : Observable<ResponseWrapperM<LeaderboardModel>>

    @GET("api/v1/users/{id}")
    fun getUserById(@Path("id") id : String) : Observable<ResponseWrapperS<UserModel>>
}