package ddowney.com.speedrunbrowser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ddowney.com.speedrunbrowser.Models.*
import ddowney.com.speedrunbrowser.Services.GameService
import ddowney.com.speedrunbrowser.Services.LeaderboardService
import ddowney.com.speedrunbrowser.Services.RunService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Entry activity for Speedrun Browser
 */
class MainActivity : AppCompatActivity() {

    val LOG_TAG = "MAIN_ACTIVITY_LOG"
    val random = Random()

    val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://speedrun.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    var bulkGames : List<BulkGameModel> = listOf()

    val runService : RunService = retrofit.create(RunService::class.java)
    val gameService : GameService = retrofit.create(GameService::class.java)
    val leaderboardService : LeaderboardService = retrofit.create(LeaderboardService::class.java)

    val gameObservable : Observable<ResponseWrapperM<BulkGameModel>> = gameService.getGamesInBulk()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    val gameConsumer = Consumer<ResponseWrapperM<BulkGameModel>> { (data) ->
        bulkGames = data
        main_button.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        getBulkGamesList()

        main_button.setOnClickListener { _ ->
            getRandomRun()
        }
    }

    /**
     * Gets a large list of games to be referenced without needing extra requests
     */
    private fun getBulkGamesList() {
        gameObservable.subscribe(gameConsumer)
    }

    /**
     * Picks out a random game from the bulk game list and gets the
     * world record run for the first category it has.
     *
     * At the moment the category might not have any runs associated and will
     * have nothing to display rather than trying another category.
     */
    private fun getRandomRun() {
        val randomGame = bulkGames[random.nextInt(bulkGames.size)]

        val catObs : Observable<ResponseWrapperM<CategoryModel>>
                = gameService.getGameCategories(randomGame.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val catCon = Consumer<ResponseWrapperM<CategoryModel>> { categories ->
            val leaderboardObservable : Observable<ResponseWrapperS<LeaderboardModel>>
                    = leaderboardService.getLeaderboardForGame(randomGame.id, categories.data[0].id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            val leaderboardConsumer = Consumer<ResponseWrapperS<LeaderboardModel>> { leaderboard ->
                if (leaderboard.data.runs.isNotEmpty()) {
                    val topRun = leaderboard.data.runs[0]
                    game_name_text.text = randomGame.names.international
                    category_name_text.text = categories.data[0].name
                    run_time_text.text = topRun.run.times.primary

                    error_layout.visibility = View.GONE
                    run_info_layout.visibility = View.VISIBLE

                    Log.d(LOG_TAG, topRun.run.weblink)
                } else {
                    error_text.text = "This category has no runs"

                    error_layout.visibility = View.VISIBLE
                    run_info_layout.visibility = View.GONE
                }
            }

            leaderboardObservable.subscribe(leaderboardConsumer)
        }

        catObs.subscribe(catCon)

    }

}
