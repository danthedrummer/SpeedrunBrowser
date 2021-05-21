package com.ddowney.speedrunbrowser.injection

import android.content.Context
import android.content.SharedPreferences
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage
import com.ddowney.speedrunbrowser.storage.Storage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class StorageModule(private val context: Context) {

  companion object {
    private const val SHARED_PREFERENCES_NAME = "speedrun_browser_preferences_name"
  }

  @Provides
  @Reusable
  fun provideSharedPreferences(): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
  }

  @Provides
  @Reusable
  fun provideSharedPreferencesStorage(
    sharedPreferences: SharedPreferences,
    gson: Gson,
  ): Storage {
    return SharedPreferencesStorage(sharedPreferences, gson)
  }

}