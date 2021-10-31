package com.ddowney.speedrunbrowser.injection.modules

import com.ddowney.speedrunbrowser.injection.qualifiers.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@InstallIn(SingletonComponent::class)
@Module
abstract class CoroutinesModule {

  companion object {

    @IoDispatcher
    @Provides
    fun ioDispatcher() = Dispatchers.IO

    @Provides
    fun coroutineScope() = CoroutineScope(SupervisorJob() + CoroutineName("speedrun-browser"))
  }
}