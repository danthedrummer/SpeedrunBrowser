package com.ddowney.speedrunbrowser.networking

import com.ddowney.speedrunbrowser.models.ListRoot
import com.ddowney.speedrunbrowser.models.Run
import io.reactivex.Observable

interface RunProvider {

    /**
     * Gets all [Run]s for a game specified by it's [gameId]
     */
    fun getRunsForGame(gameId: String): Observable<ListRoot<Run>>

}