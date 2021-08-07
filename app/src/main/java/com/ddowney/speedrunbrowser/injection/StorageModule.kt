package com.ddowney.speedrunbrowser.injection

import android.content.Context
import android.content.SharedPreferences
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage
import com.ddowney.speedrunbrowser.storage.Storage
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class StorageModule {

  companion object {
    private const val SHARED_PREFERENCES_NAME = "speedrun_browser_preferences_name"

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
      return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
  }

  @Binds
  @Singleton
  abstract fun sharedPreferencesStorage(sharedPreferencesStorage: SharedPreferencesStorage): Storage
}