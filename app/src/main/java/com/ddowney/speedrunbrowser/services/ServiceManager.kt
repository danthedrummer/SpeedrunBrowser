package com.ddowney.speedrunbrowser.services

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dan on 27/10/2017.
 */
object ServiceManager {

    private val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://speedrun.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    val gameService : GameService = retrofit.create(GameService::class.java)
    val runService : RunService = retrofit.create(RunService::class.java)


}