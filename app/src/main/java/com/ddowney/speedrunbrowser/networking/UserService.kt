package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.models.ObjectRoot
import com.ddowney.speedrunbrowser.models.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserService {

  @Headers("user-agent: ${BuildConfig.USER_AGENT_HEADER}")
  @GET("api/v1/users/{id}")
  fun getUser(@Path("id") id: String): Observable<ObjectRoot<User>>

}