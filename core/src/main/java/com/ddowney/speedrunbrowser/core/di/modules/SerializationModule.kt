package com.ddowney.speedrunbrowser.core.di.modules

import com.ddowney.speedrunbrowser.core.network.responses.GameResponse
import com.ddowney.speedrunbrowser.core.serialization.GameResponseDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SerializationModule {

  /**
   * Simple instance of [Gson] which contains no special converters or deserializers
   */
  @Singleton
  @Provides
  @BaseGson
  fun baseGson(): Gson = Gson()

  @Singleton
  @Provides
  @NetworkGson
  fun networkGson(
    gameResponseDeserializer: GameResponseDeserializer,
  ): Gson = GsonBuilder()
    .registerTypeAdapter(GameResponse::class.java, gameResponseDeserializer)
    .create()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class BaseGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class NetworkGson

