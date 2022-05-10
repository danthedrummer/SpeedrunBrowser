package com.ddowney.speedrunbrowser.core.di.modules

import com.ddowney.speedrunbrowser.core.BuildConfig
import com.ddowney.speedrunbrowser.core.network.services.GameService
import com.ddowney.speedrunbrowser.core.network.services.LeaderboardService
import com.ddowney.speedrunbrowser.core.network.services.RunService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {
  companion object {

    @Singleton
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun okhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
      .apply {
        if (BuildConfig.DEBUG) {
          addInterceptor(loggingInterceptor)
        }
      }
      .build()

    @Singleton
    @Provides
    fun retrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.SPEEDRUN_BASE_URL)
      .client(httpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    @Singleton
    @Provides
    fun gameService(retrofit: Retrofit): GameService = retrofit.create()

    @Singleton
    @Provides
    fun leaderboardService(retrofit: Retrofit): LeaderboardService = retrofit.create()

    @Reusable
    @Provides
    fun runService(retrofit: Retrofit): RunService = retrofit.create()
  }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class UserAgent

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class BaseUrl