package com.ddowney.speedrunbrowser.injection.modules

import com.ddowney.speedrunbrowser.BuildConfig
import com.ddowney.speedrunbrowser.utils.DebugLogger
import com.ddowney.speedrunbrowser.utils.Logger
import com.ddowney.speedrunbrowser.utils.StubLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DebugModule {

  companion object {
    @Provides
    fun logger(): Logger = if (BuildConfig.DEBUG) DebugLogger() else StubLogger()
  }
}