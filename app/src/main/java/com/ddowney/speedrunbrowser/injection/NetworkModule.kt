package com.ddowney.speedrunbrowser.injection

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.networking.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

  companion object {
    const val BASE_URL = "https://www.speedrun.com/"
    const val USER_AGENT_VALUE = BuildConfig.USER_AGENT_HEADER
  }

  @Provides
  @Reusable
  fun provideGson(): Gson {
    return Gson()
  }

  @Provides
  @Reusable
  fun provideUserAgentHeaderInterceptor(): UserAgentHeaderInterceptor {
    return UserAgentHeaderInterceptor(USER_AGENT_VALUE)
  }

  @Provides
  @Reusable
  fun provideBaseOkHttpClient(userAgentHeader: UserAgentHeaderInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(userAgentHeader)
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Reusable
  fun provideCategoriesProvider(client: OkHttpClient, gson: Gson): CategoriesProvider {
    return CategoriesProviderImpl(client, BASE_URL, gson)
  }

  @Provides
  @Reusable
  fun provideGameProvider(client: OkHttpClient, gson: Gson): GameProvider {
    return GameProviderImpl(client, BASE_URL, gson)
  }

  @Provides
  @Reusable
  fun provideRunProvider(client: OkHttpClient, gson: Gson): RunProvider {
    return RunProviderImpl(client, BASE_URL, gson)
  }

  @Provides
  @Reusable
  fun provideUserProvider(client: OkHttpClient, gson: Gson): UserProvider {
    return UserProviderImpl(client, BASE_URL, gson)
  }

}