package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.Category
import com.ddowney.speedrunbrowser.models.Game
import com.ddowney.speedrunbrowser.models.ListRoot
import io.reactivex.Observable

interface GameProvider {

    /**
     * Get all [Game] objects filtered by the given [options]. Options can be things like name or
     * genre. E.g. mapOf(Pair("name", "mario")) should return games containing the name "mario"
     */
    fun getGames(options: Map<String, String>): Observable<ListRoot<Game>>

    /**
     * Get all [Category] objects for the game belonging to the given [id]
     */
    fun getCategoriesForGame(id: String): Observable<ListRoot<Category>>

}