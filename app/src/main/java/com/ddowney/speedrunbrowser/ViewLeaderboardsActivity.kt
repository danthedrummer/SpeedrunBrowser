package com.ddowney.speedrunbrowser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListAdapter
import android.widget.Toast
import com.ddowney.speedrunbrowser.MainActivity.Companion.LOG_TAG
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.CATEGORY_NAME_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.POSITION_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RUN_EXTRA
import com.ddowney.speedrunbrowser.adapters.ExpandingCategoryListAdapter
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.Storage
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_game.*

class ViewLeaderboardsActivity : AppCompatActivity() {

    companion object {
        val GAME_EXTRA = "GAME_EXTRA"
    }

    private lateinit var game : GameModel

    private lateinit var expListAdapter : ExpandingCategoryListAdapter
    private lateinit var listHeaders: MutableList<CategoriesModel>
    private var listChildren = mutableMapOf<String, List<LeaderboardModel.RunPosition>>()

    private var lastExpandedGroup = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)
        setSupportActionBar(game_toolbar)

        game_toolbar.title = "Leaderboards"

        val bundle = this.intent.extras
        if (bundle != null) {
            game = bundle.getSerializable(GAME_EXTRA) as GameModel
        }

        val categoryObservable = ServiceManager.gameService.getCategoriesForGame(game.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val categoriesConsumer = Consumer<ResponseWrapperM<CategoriesModel>> { (data) ->
            listHeaders = data.toMutableList()

            data.forEach { category ->
                val obs = ServiceManager.catergoryService.getRecordsForCategory(category.id, 5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                val con = Consumer<ResponseWrapperM<LeaderboardModel>> { record ->
                    if (record.data.isEmpty()) {
                        listHeaders.remove(category)
                    } else {
                        record.data.forEach {
                            listChildren.put(category.name, it.runs)
                        }
                    }
                    updateListAdapter()
                }
                obs.subscribe(con)
            }
        }

        categoryObservable.subscribe(categoriesConsumer)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_favourite -> {
                Log.d(LOG_TAG, "Favourite clicked")
                when(updateFavourites(game)) {
                    1 -> { Toast.makeText(this, "Added to favourites!", Toast.LENGTH_SHORT).show() }
                    -1 -> { Toast.makeText(this, "Removed from favourites!", Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show() }
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Updates the user favourites by adding the game if it is not already
     * added to favourites or removing it if it is
     */
    private fun updateFavourites(game : GameModel) : Int {
        val result: Int
        val storage = Storage(this)
        val updatedFavourites = storage.readListFromStorage(Storage.FAVOURITES_KEY,
                object: TypeToken<List<GameModel>>() {}).toMutableList()

        result = if (updatedFavourites.contains(game)) {
            updatedFavourites.remove(game)
            -1
        } else {
            updatedFavourites.add(game)
            1
        }

        storage.writeListToStorage(Storage.FAVOURITES_KEY, updatedFavourites,
                object: TypeToken<List<GameModel>>() {})
        return result
    }

    private fun updateListAdapter() {
        expListAdapter = ExpandingCategoryListAdapter(this, listHeaders, listChildren)
        expandable_category_list.setAdapter(expListAdapter)

        expandable_category_list.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val intent = Intent(this, ViewRunActivity::class.java)
            val bundle = Bundle()

            val cat : String = listHeaders[groupPosition].name
            bundle.putString(CATEGORY_NAME_EXTRA, cat)

            val pos : Int = childPosition + 1
            bundle.putInt(POSITION_EXTRA, pos)

            val run : RunModel? = listChildren[cat]?.get(childPosition)?.run
            if (run != null) {
                Log.d("wubalub", run.toString())
                bundle.putSerializable(RUN_EXTRA, run)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            true
        }

        expandable_category_list.setOnGroupExpandListener { groupPosition ->
            if (lastExpandedGroup != -1 && groupPosition != lastExpandedGroup) {
                expandable_category_list.collapseGroup(lastExpandedGroup)
            }
            lastExpandedGroup = groupPosition
        }
    }

}
