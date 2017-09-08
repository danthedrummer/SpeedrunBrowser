package ddowney.com.speedrunbrowser.Services

import ddowney.com.speedrunbrowser.Models.BulkGameModel
import ddowney.com.speedrunbrowser.Models.CategoryModel
import ddowney.com.speedrunbrowser.Models.ResponseWrapperM
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GameService {

    /**
     * Gets the minimised data for games with speedruns. Plan is to store this list and
     * select games randomly from this list.
     *
     * By default this only returns 250 items which is plenty for now.
     *
     * I'm not sure if this means that some games will never show up because it only
     * delivers the first 250 but I can try messing with ordering and filtering
     * to increase the coverage of the bulk access.
     *
     * To increase the size of the return type use:
     * @GET("api/v1/games?_bulk=yes&max=1000")
     */
    @GET("api/v1/games?_bulk=yes")
    fun getGamesInBulk() : Observable<ResponseWrapperM<BulkGameModel>>

    /**
     * Gets all categories for a specific game
     *
     * @param game The id of the game
     */
    @GET("api/v1/games/{game}/categories")
    fun getGameCategories(@Path("game") game : String) : Observable<ResponseWrapperM<CategoryModel>>

}