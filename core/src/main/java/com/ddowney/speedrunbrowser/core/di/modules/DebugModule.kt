package com.ddowney.speedrunbrowser.core.di.modules

import com.ddowney.speedrunbrowser.core.BuildConfig
import com.ddowney.speedrunbrowser.core.utils.DebugLogger
import com.ddowney.speedrunbrowser.core.utils.Logger
import com.ddowney.speedrunbrowser.core.utils.StubLogger
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DebugModule {

  companion object {
    @Provides
    @Reusable
    fun logger(): Logger = if (BuildConfig.DEBUG) DebugLogger() else StubLogger()
  }
}