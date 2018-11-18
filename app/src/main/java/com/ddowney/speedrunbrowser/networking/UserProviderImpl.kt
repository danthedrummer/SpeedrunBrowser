package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.ObjectRoot
import com.ddowney.speedrunbrowser.models.User
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.OkHttpClient

class UserProviderImpl(
        client: OkHttpClient,
        baseUrl: String,
        gson: Gson
) : BaseProvider(client, baseUrl, gson), UserProvider {

    private val service = retrofit.create(UserService::class.java)

    override fun getUser(id: String): Observable<ObjectRoot<User>> {
        return service.getUser(id)
    }

}