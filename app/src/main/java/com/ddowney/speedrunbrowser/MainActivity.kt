package com.ddowney.speedrunbrowser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ddowney.speedrunbrowser.ViewCategoriesActivity.Companion.GAME_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.CATEGORY_NAME_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.POSITION_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RANDOM_RUN_EXTRA
import com.ddowney.speedrunbrowser.adapters.GameListAdapter
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.services.ServiceManager.errorConsumer
import com.ddowney.speedrunbrowser.storage.Storage
import com.ddowney.speedrunbrowser.utils.JsonResourceReader
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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
    private var platformList : List<PlatformModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        if (gameList.isEmpty()) {
            gameList = JsonResourceReader(resources, R.raw.all_games, gson)
                    .constructUsingGson(object : TypeToken<ResponseWrapperM<GameModel>>() {})
                    .data

            platformList = JsonResourceReader(resources, R.raw.all_platforms, gson)
                        .constructUsingGson(object : TypeToken<ResponseWrapperM<PlatformModel>>() {})
                        .data

            gameList.forEach { game ->
                val newPlatformList = mutableListOf<String>()
                game.platforms?.forEach { platformId ->
                    platformList
                            .filter { it.id == platformId }
                            .forEach { newPlatformList.add(it.name) }
                }
                game.platforms = newPlatformList
            }

        }

        changeGameListData(gameList)

        main_recycler.setHasFixedSize(true)
        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = gameListAdapter
        main_recycler.hasPendingAdapterUpdates()

        random_fab.setOnClickListener({
            pickRandomRun()
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
                data.forEach { game ->
                    val newPlatformList = mutableListOf<String>()
                    game.platforms?.forEach { platformId ->
                        platformList
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
            R.id.action_show_favourites -> {
                main_toolbar.title = resources.getText(R.string.favourites)
                displayFavourites()
                true
            }

            R.id.action_clear_all -> {
                main_toolbar.title = resources.getText(R.string.games_title)
                Toast.makeText(this, resources.getText(R.string.resetting_list), Toast.LENGTH_SHORT).show()
                changeGameListData(gameList)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun displayFavourites() {
        val storage = Storage(this)
        val favourites = storage.readListFromStorage(Storage.FAVOURITES_KEY, object: TypeToken<List<String>>() {})

        if (!favourites.isEmpty()) {
            val displayList = mutableListOf<GameModel>()
                favourites.forEach { gameId ->
                    gameList.filter { gameId == it.id }.map { displayList.add(it) }
            }
            changeGameListData(displayList)
        } else {
            Toast.makeText(this, resources.getText(R.string.no_favourites_saved), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Updates the recycler view adapter
     */
    private fun changeGameListData(data : List<GameModel>) {
        gameListAdapter = GameListAdapter(data) { game ->
            val runIntent = Intent(this, ViewCategoriesActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(ViewCategoriesActivity.GAME_EXTRA, game)
            runIntent.putExtras(bundle)
            startActivity(runIntent)
        }
        main_recycler.adapter = gameListAdapter
    }

    /**
     * Picks a random speedrun for the user to watch
     */
    private fun pickRandomRun() {
        val randomGame = gameList[random.nextInt(gameList.size-1)]

        val categoriesObserver = ServiceManager.gameService.getCategoriesForGame(randomGame.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val categoriesConsumer = Consumer<ResponseWrapperM<CategoriesModel>> { (categories) ->
            val randomCategory = categories[random.nextInt(categories.size)]

            val recordsObserver = ServiceManager.categoriesService.getRecordsForCategory(randomCategory.id, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            val recordsConsumer = Consumer<ResponseWrapperM<LeaderboardModel>> { (records) ->
                if (records.isNotEmpty() && records[0].runs.isNotEmpty()) {
                    val intent = Intent(this, ViewRunActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable(ViewRunActivity.RUN_EXTRA, records[0].runs[0].run)
                    bundle.putBoolean(RANDOM_RUN_EXTRA, true)
                    bundle.putString(CATEGORY_NAME_EXTRA, randomCategory.name)
                    bundle.putSerializable(GAME_EXTRA, randomGame)
                    bundle.putInt(POSITION_EXTRA, 1)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, resources.getText(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            }

            recordsObserver.subscribe(recordsConsumer, errorConsumer)
        }

        categoriesObserver.subscribe(categoriesConsumer, errorConsumer)
    }

}
