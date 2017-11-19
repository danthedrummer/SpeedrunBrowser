package com.ddowney.speedrunbrowser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RANDOM_RUN
import com.ddowney.speedrunbrowser.adapters.GameListAdapter
import com.ddowney.speedrunbrowser.adapters.PlatformListAdapter
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.TempDataStore
import com.ddowney.speedrunbrowser.utils.JsonResourceReader
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.leakcanary.LeakCanary
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Entry activity for Speedrun Browser
 */
class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = "MAIN_ACTIVITY_LOG"
    }

    private val random = Random()

    private lateinit var gameListAdapter : GameListAdapter
    private lateinit var platformListAdapter : PlatformListAdapter

    private val gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this.application)

        if (TempDataStore.gamesList.isEmpty()) {
            TempDataStore.gamesList = JsonResourceReader(resources, R.raw.all_games, gson)
                    .constructUsingGson(object : TypeToken<ResponseWrapperM<GameModel>>() {})
                    .data
        }

        if (TempDataStore.platformsList.isEmpty()) {
            TempDataStore.platformsList = JsonResourceReader(resources, R.raw.all_platforms, gson)
                    .constructUsingGson(object : TypeToken<ResponseWrapperM<PlatformModel>>() {})
                    .data
        }

        changeGameListData(TempDataStore.gamesList)

        platformListAdapter = PlatformListAdapter(TempDataStore.platformsList) { x ->
            Log.d(LOG_TAG, "${x.name} clicked!")
        }

        main_recycler.setHasFixedSize(true)
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = gameListAdapter
        main_recycler.hasPendingAdapterUpdates()

        random_fab.setOnClickListener({
            val runIntent = Intent(this, ViewRunActivity::class.java)
            runIntent.putExtra(RANDOM_RUN, true)
            startActivity(runIntent)
        })

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY) ?: ""

            val gameObservable = ServiceManager.gameService.searchForGamesByName(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            val gameConsumer = Consumer<ResponseWrapperM<GameModel>> { (data) ->
                changeGameListData(data)
            }

            gameObservable.subscribe(gameConsumer)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_settings -> {
                Log.d(LOG_TAG, "Settings clicked")
                true
            }

            R.id.action_search -> {
                Log.d(LOG_TAG, "Search clicked")
                true
            }

            R.id.action_favourites -> {
                changeGameListData(TempDataStore.fakeFavouriteGames.value)
                true
            }

            R.id.action_clear_all -> {
                changeGameListData(TempDataStore.gamesList)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun changeGameListData(data : List<GameModel>) {
        gameListAdapter = GameListAdapter(data) { game ->
            val runIntent = Intent(this, ViewLeaderboardsActivity::class.java)
            runIntent.putExtra(ViewLeaderboardsActivity.GAME_ID_EXTRA, game.id)
            startActivity(runIntent)
        }
        main_recycler.adapter = gameListAdapter
    }

}
