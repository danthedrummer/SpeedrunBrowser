package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.Leaderboard
import com.ddowney.speedrunbrowser.models.ListRoot
import io.reactivex.Observable

interface CategoriesProvider {

    /**
     * Gets the [top] amount of [Leaderboard] objects for a category specified by it's category [id]
     */
    fun getRecordsForCategory(id: String, top: Int): Observable<ListRoot<Leaderboard>>

}