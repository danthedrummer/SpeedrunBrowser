package com.ddowney.speedrunbrowser.core.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class CoroutineModule {

  companion object {

    @IoDispatcher
    @Provides
    fun ioDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun coroutineScope() = CoroutineScope(SupervisorJob() + CoroutineName("speedrun-browser"))
  }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class IoDispatcher