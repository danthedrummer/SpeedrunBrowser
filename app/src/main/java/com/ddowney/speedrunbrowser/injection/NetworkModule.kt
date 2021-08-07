package com.ddowney.speedrunbrowser.injection

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.networking.*
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
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
        .addInterceptor(HttpLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
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

  @Binds
  @Singleton
  abstract fun categoriesProvider(impl: CategoriesProviderImpl): CategoriesProvider

  @Binds
  @Singleton
  abstract fun gameProvider(impl: GameProviderImpl): GameProvider

  @Binds
  @Singleton
  abstract fun runProvider(impl: RunProviderImpl): RunProvider

  @Binds
  @Singleton
  abstract fun userProvider(impl: UserProviderImpl): UserProvider
}
