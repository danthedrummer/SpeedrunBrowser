package com.ddowney.speedrunbrowser.storage

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * [SharedPreferences] backed implementation of [Storage]. Uses [Gson] for serialisation.
 */
class SharedPreferencesStorage(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) : Storage {

    companion object {
        const val PREFERENCES_NAME: String = "SpeedrunBrowserSharedPreferences"
        const val ALL_GAMES_KEY = "ALL_GAMES_KEY"
        const val ALL_PLATFORMS_KEY = "ALL_PLATFORMS_KEY"
        const val FAVOURITES_KEY = "FAVOURITES_KEY"
    }

    override fun put(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun put(key: String, value: Any) {
        val editor = sharedPreferences.edit()
        val valueString = gson.toJson(value)
        editor.putString(key, valueString)
        editor.apply()
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun <T> get(key: String, clazz: Class<T>): T? {
        val storedString = sharedPreferences.getString(key, null)
        return if (storedString == null) {
            null
        } else {
            gson.fromJson(storedString, clazz)
        }
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}