package com.ddowney.speedrunbrowser.injection.modules

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.injection.qualifiers.BaseUrl
import com.ddowney.speedrunbrowser.injection.qualifiers.UserAgent
import com.ddowney.speedrunbrowser.networking.UserAgentHeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

  companion object {

    @Provides
    @Singleton
    fun baseOkHttpClient(userAgentHeader: UserAgentHeaderInterceptor): OkHttpClient {
      return OkHttpClient.Builder()
        .addInterceptor(userAgentHeader)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
    }

    @Provides
    @Singleton
    fun gson() = Gson()

    @BaseUrl
    @Provides
    fun baseUrl() = "https://www.speedrun.com/"

    @UserAgent
    @Provides
    fun userAgent() = BuildConfig.USER_AGENT_HEADER
  }
}
