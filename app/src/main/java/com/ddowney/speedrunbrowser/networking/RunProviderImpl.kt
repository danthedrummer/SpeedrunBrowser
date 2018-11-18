package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.ListRoot
import com.ddowney.speedrunbrowser.models.Run
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.OkHttpClient

class RunProviderImpl(
        client: OkHttpClient,
        baseUrl: String,
        gson: Gson
) : BaseProvider(client, baseUrl, gson), RunProvider {

    private val service = retrofit.create(RunService::class.java)

    override fun getRunsForGame(gameId: String): Observable<ListRoot<Run>> {
        return service.getRunsForGame(gameId)
    }

}