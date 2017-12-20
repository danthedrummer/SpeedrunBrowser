package com.ddowney.speedrunbrowser.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Dan on 19/12/2017.
 */
class Storage(val context: Context) {

    companion object {
        private val PREFERENCES: String = "SpeedrunBrowserPreferences"
        val ALL_GAMES_KEY = "ALL_GAMES_KEY"
        val ALL_PLATFORMS_KEY = "ALL_PLATFORMS_KEY"
        val FAVOURITES_KEY = "FAVOURITES_KEY"
    }

    private val sharedPrefs : SharedPreferences
            = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    private val gson : Gson = Gson()

    /**
     * Reads list from storage using the specified key
     */
    fun <T> readListFromStorage(key : String, token: TypeToken<List<T>>) : List<T> {
        val storedString = sharedPrefs.getString(key, "")
        if (storedString.isEmpty()) {
            return listOf()
        }
        return gson.fromJson(storedString, token.type)
    }

    /**
     * Write the specified list to shared preferences
     */
    fun <T> writeListToStorage(key : String, data : List<T>, token: TypeToken<List<T>>) {
        val editor = sharedPrefs.edit()
        editor.putString(key, gson.toJson(data, token.type))
        editor.apply()
    }
}