package com.ddowney.speedrunbrowser.services

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.models.Categories
import com.ddowney.speedrunbrowser.models.Game
import com.ddowney.speedrunbrowser.models.ListRoot
import io.reactivex.Observable
import retrofit2.http.*

interface GameService {

    @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
    @GET("api/v1/games")
    fun getGames(@QueryMap options: Map<String, String>): Observable<ListRoot<Game>>

    @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
    @GET("api/v1/games")
    fun searchForGamesByName(@Query("name") name: String): Observable<ListRoot<Game>>

    @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
    @GET("api/v1/games/{id}/categories")
    fun getCategoriesForGame(@Path("id") id: String): Observable<ListRoot<Categories>>
}