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
    const val BASE_URL = "https://www.speedrun.com/"
    const val USER_AGENT_VALUE = BuildConfig.USER_AGENT_HEADER

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
    @Singleton
    fun baseUrl() = BASE_URL

    @UserAgent
    @Provides
    @Singleton
    fun userAgent() = USER_AGENT_VALUE
  }

  @Binds
  @Singleton
  abstract fun provideCategoriesProvider(impl: CategoriesProviderImpl): CategoriesProvider

  @Binds
  @Singleton
  abstract fun provideGameProvider(impl: GameProviderImpl): GameProvider

  @Binds
  @Singleton
  abstract fun provideRunProvider(impl: RunProviderImpl): RunProvider

  @Binds
  @Singleton
  abstract fun provideUserProvider(impl: UserProviderImpl): UserProvider
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserAgent