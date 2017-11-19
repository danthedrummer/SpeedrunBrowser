package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.models.CategoriesModel
import com.ddowney.speedrunbrowser.models.GameModel
import com.ddowney.speedrunbrowser.models.ResponseWrapperM
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GameService {

    @GET("api/v1/games")
    fun getGames(@QueryMap options : Map<String, String>) : Observable<ResponseWrapperM<GameModel>>

    @GET("api/v1/games")
    fun searchForGamesByName(@Query("name") name : String) : Observable<ResponseWrapperM<GameModel>>

    @GET("api/v1/games/{id}/categories")
    fun getCategoriesForGame(@Path("id") id : String) : Observable<ResponseWrapperM<CategoriesModel>>
}