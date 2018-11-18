package com.ddowney.speedrunbrowser.networking

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Base provider class which builds a valid instance of [Retrofit] with the given instance of
 * [OkHttpClient], the base url for the API, and an instance of [Gson] configured as necessary
 */
open class BaseProvider(client: OkHttpClient, baseUrl: String, gson: Gson) {

    protected val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

}