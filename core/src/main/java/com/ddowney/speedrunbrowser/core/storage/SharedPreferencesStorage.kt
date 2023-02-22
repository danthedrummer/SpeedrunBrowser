package com.ddowney.speedrunbrowser.core.storage

import android.content.SharedPreferences
import com.ddowney.speedrunbrowser.core.di.modules.BaseGson
import com.ddowney.speedrunbrowser.core.di.modules.IoDispatcher
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * [SharedPreferences] backed implementation of [Storage]. Uses [Gson] for serialisation.
 */
internal class SharedPreferencesStorage @Inject constructor(
  private val sharedPreferences: SharedPreferences,
  @BaseGson private val gson: Gson,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : Storage {

  private val editor: SharedPreferences.Editor
    get() = sharedPreferences.edit()

  override suspend fun put(key: String, value: String) {
    withContext(ioDispatcher) {
      editor.putString(key, value).apply()
    }
  }

  override suspend fun put(key: String, value: Any) {
    withContext(ioDispatcher) {
      editor.putString(key, gson.toJson(value)).apply()
    }
  }

  override suspend fun get(key: String): String? = withContext(ioDispatcher) {
    sharedPreferences.getString(key, null)
  }

  override suspend fun <T> get(key: String, clazz: Class<T>): T? = withContext(ioDispatcher) {
    sharedPreferences.getString(key, null)
      ?.let { gson.fromJson(it, clazz) }
  }

  override suspend fun remove(key: String) {
    withContext(ioDispatcher) {
      editor.remove(key).apply()
    }
  }

  override suspend fun clear() {
    withContext(ioDispatcher) {
      editor.clear().apply()
    }
  }
}