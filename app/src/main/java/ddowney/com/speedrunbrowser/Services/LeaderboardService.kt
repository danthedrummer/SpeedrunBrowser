package ddowney.com.speedrunbrowser.Services

import ddowney.com.speedrunbrowser.Models.LeaderboardModel
import ddowney.com.speedrunbrowser.Models.ResponseWrapperS
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LeaderboardService {

    /**
     * Gets the leaderboard for a specific category of a game
     *
     * @param game the id of the game
     * @param category the id of the category
     */
    @GET("api/v1/leaderboards/{game}/category/{category}")
    fun getLeaderboardForGame(@Path("game") game : String, @Path("category") category : String)
            : Observable<ResponseWrapperS<LeaderboardModel>>
}