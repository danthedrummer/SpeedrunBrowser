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
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.Storage
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

    private val gson = GsonBuilder().create()

    private var gameList : List<GameModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this.application)

        val storage = Storage(this)

        gameList = storage.readListFromStorage(Storage.ALL_GAMES_KEY, object: TypeToken<List<GameModel>>(){})
        if (gameList.isEmpty()) {
            val rawGameList = JsonResourceReader(resources, R.raw.all_games, gson)
                    .constructUsingGson(object : TypeToken<ResponseWrapperM<GameModel>>() {})
                    .data

            val rawPlatformList = JsonResourceReader(resources, R.raw.all_platforms, gson)
                        .constructUsingGson(object : TypeToken<ResponseWrapperM<PlatformModel>>() {})
                        .data

            rawGameList.forEach { game ->
                val newPlatformList = mutableListOf<String>()
                game.platforms?.forEach { platformId ->
                    rawPlatformList
                            .filter { it.id == platformId }
                            .forEach { newPlatformList.add(it.name) }
                }
                game.platforms = newPlatformList
            }

            storage.writeListToStorage(Storage.ALL_GAMES_KEY, rawGameList, object: TypeToken<List<GameModel>>(){})
            storage.writeListToStorage(Storage.ALL_PLATFORMS_KEY, rawPlatformList, object: TypeToken<List<PlatformModel>>(){})
            gameList = rawGameList
        }

        changeGameListData(gameList)

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
                val storage = Storage(this)
                val platformsList = storage.readListFromStorage(Storage.ALL_PLATFORMS_KEY, object: TypeToken<List<PlatformModel>>() {})
                data.forEach { game ->
                    val newPlatformList = mutableListOf<String>()
                    game.platforms?.forEach { platformId ->
                        platformsList
                                .filter { it.id == platformId }
                                .forEach { newPlatformList.add(it.name) }
                    }
                    game.platforms = newPlatformList
                }
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
            R.id.action_search -> {
                Log.d(LOG_TAG, "Search clicked")
                true
            }

            R.id.action_show_favourites -> {
                val storage = Storage(this)
                val favourites = storage.readListFromStorage(Storage.FAVOURITES_KEY, object: TypeToken<List<GameModel>>() {})
                changeGameListData(favourites)
                true
            }

            R.id.action_clear_all -> {
                changeGameListData(gameList)
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
            val bundle = Bundle()
            bundle.putSerializable(ViewLeaderboardsActivity.GAME_EXTRA, game)
            runIntent.putExtras(bundle)
            startActivity(runIntent)
        }
        main_recycler.adapter = gameListAdapter
    }

}
