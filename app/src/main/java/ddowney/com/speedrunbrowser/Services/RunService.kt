package ddowney.com.speedrunbrowser.Services

import ddowney.com.speedrunbrowser.Models.ResponseWrapperM
import ddowney.com.speedrunbrowser.Models.RunModel
import ddowney.com.speedrunbrowser.RunStatus
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service for getting runs from the Speedrun API
 */
interface RunService {

    /**
     * Gets all runs
     *
     * I'm not sure how many runs total this will return so let's not use it
     */
    @GET("api/v1/runs")
    fun getRuns() : Observable<ResponseWrapperM<RunModel>>

    /**
     * Gets runs with a specific {@link RunStatus}
     *
     * @param status the status of the run (i.e. new, verified or rejected)
     */
    @GET("api/v1/runs")
    fun getRuns(@Query("status") status : RunStatus) : Observable<ResponseWrapperM<RunModel>>

    /**
     * Get run with specific ID
     *
     * @param id the id of the run
     */
    @GET("api/v1/runs/{id}")
    fun getRunWithId(@Path("id") id : String) : Observable<ResponseWrapperM<RunModel>>
}