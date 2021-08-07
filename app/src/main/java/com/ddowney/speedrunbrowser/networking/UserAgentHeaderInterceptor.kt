package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.injection.UserAgent
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This header is not required for interacting with the Speedrun.com api but we should be
 * good citizens and announce who we are in case there's an issue later down the line.
 * The idea is that we can pass along the applicationId as the [headerValue] so we can tell
 * that the traffic is from this app and possibly even which version of the app.
 */
class UserAgentHeaderInterceptor @Inject constructor(
  @UserAgent private val headerValue: String,
) : Interceptor {

  companion object {
    const val USER_AGENT_KEY = "user-agent"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val requestHeaderBuilder = chain.request().newBuilder()

    requestHeaderBuilder.addHeader(USER_AGENT_KEY, headerValue)

    return chain.proceed(requestHeaderBuilder.build())
  }
}