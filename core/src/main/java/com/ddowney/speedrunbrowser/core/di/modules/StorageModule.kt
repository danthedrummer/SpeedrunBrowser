package com.ddowney.speedrunbrowser.core.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ddowney.speedrunbrowser.core.db.SpeedrunBrowserDatabase
import com.ddowney.speedrunbrowser.core.db.converter.Converters
import com.ddowney.speedrunbrowser.core.storage.SharedPreferencesStorage
import com.ddowney.speedrunbrowser.core.storage.Storage
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
internal interface StorageModule {

  @Binds
  @Singleton
  fun sharedPreferencesStorage(sharedPreferencesStorage: SharedPreferencesStorage): Storage

  companion object {
    private const val SHARED_PREFERENCES_NAME = "speedrun_browser_preferences_name"

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
      return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Reusable
    fun database(
      @ApplicationContext context: Context,
      converters: Converters,
    ): SpeedrunBrowserDatabase {
      return Room.databaseBuilder(
        context,
        SpeedrunBrowserDatabase::class.java,
        "speedrun-browser-database",
      )
        .addTypeConverter(converters)
        .build()
    }
  }
}