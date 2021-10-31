package com.ddowney.speedrunbrowser.core.network.interceptors

import com.ddowney.speedrunbrowser.core.di.modules.UserAgent
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * This header is not required for interacting with the Speedrun.com api but we should be
 * good citizens and announce who we are in case there's an issue later down the line.
 * The idea is that we can pass along the applicationId as the [headerValue] so we can tell
 * that the traffic is from this app and possibly even which version of the app.
 */
internal class UserAgentHeaderInterceptor @Inject constructor(
  @UserAgent private val headerValue: String,
) : Interceptor {

  companion object {
    const val USER_AGENT_KEY = "user-agent"
  }

  override fun intercept(chain: Interceptor.Chain): Response = chain.request().newBuilder()
    .addHeader(USER_AGENT_KEY, headerValue)
    .build()
    .let(chain::proceed)
}