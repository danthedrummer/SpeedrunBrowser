package com.ddowney.speedrunbrowser.storage

import android.util.Log
import com.ddowney.speedrunbrowser.models.GameModel
import com.ddowney.speedrunbrowser.models.PlatformModel

/**
 * Created by Dan on 31/10/2017.
 *
 * Temporary way for me to store data across classes. Will be replacing with SharedPrefs
 */
object TempDataStore {

    var gamesList : List<GameModel> = listOf()
    var platformsList : List<PlatformModel> = listOf()

    /**
     * Gets a platform name by it's id
     * Most likely will always return a value but there's a chance it be empty
     *
     * @param id The id of the platform
     * @return the name of the platform
     */
    fun getPlatformById(id : String) : String {
        platformsList
                .filter { it.id == id }
                .forEach { return it.name }
        return ""
    }

    fun getGameNameById(id : String) : String {
        gamesList
                .filter { it.id == id }
                .forEach { return it.names.international }
        return ""
    }

    fun updateEntries(data : List<GameModel>) {
        data.forEach { newGame ->
            gamesList.forEach { oldGame ->
                if (oldGame.id == newGame.id) {
                    oldGame.platforms = newGame.platforms
                }
            }
        }
    }

}