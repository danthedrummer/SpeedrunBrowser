package com.ddowney.speedrunbrowser.core.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

/**
 * [SharedPreferences] backed implementation of [Storage]. Uses [Gson] for serialisation.
 */
internal class SharedPreferencesStorage @Inject constructor(
  private val sharedPreferences: SharedPreferences,
  private val gson: Gson,
) : Storage {

  private val editor: SharedPreferences.Editor
    get() = sharedPreferences.edit()

  override fun put(key: String, value: String) {
    editor.putString(key, value).apply()
  }

  override fun put(key: String, value: Any) {
    editor.putString(key, gson.toJson(value)).apply()
  }

  override fun get(key: String): String? = sharedPreferences.getString(key, null)

  override fun <T> get(key: String, clazz: Class<T>): T? = sharedPreferences.getString(key, null)
    ?.let { gson.fromJson(it, clazz) }

  override fun remove(key: String) {
    editor.remove(key).apply()
  }

  override fun clear() {
    editor.clear().apply()
  }
}