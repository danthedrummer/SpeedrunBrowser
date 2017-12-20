package com.ddowney.speedrunbrowser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.CATEGORY_NAME_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.POSITION_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RUN_EXTRA
import com.ddowney.speedrunbrowser.adapters.ExpandingCategoryListAdapter
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.Storage
import com.ddowney.speedrunbrowser.storage.Storage.Companion.FAVOURITES_KEY
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_category.*

class ViewCategoriesActivity : AppCompatActivity() {

    companion object {
        val GAME_EXTRA = "GAME_EXTRA"
    }

    private lateinit var game : GameModel

    private lateinit var expListAdapter : ExpandingCategoryListAdapter
    private lateinit var listHeaders: MutableList<CategoriesModel>
    private var listChildren = mutableMapOf<String, List<LeaderboardModel.RunPosition>>()

    private var lastExpandedGroup = -1

    private lateinit var menu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_category)
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
            var responses = 0

            for (i in 0 until data.size) {
                val recordsObserver = ServiceManager.catergoryService.getRecordsForCategory(data[i].id, 5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                val recordsConsumer = Consumer<ResponseWrapperM<LeaderboardModel>> { record ->
                    if (record.data.isEmpty()) {
                        listHeaders.remove(data[i])
                    } else {
                        record.data.forEach {
                            listChildren.put(data[i].name, it.runs)
                        }
                    }
                    responses++
                    if(responses == data.size) {
                        updateListAdapter()
                        run_loading.visibility = View.GONE
                        expandable_category_list.visibility = View.VISIBLE
                    }
                }
                recordsObserver.subscribe(recordsConsumer)
            }
        }

        categoryObservable.subscribe(categoriesConsumer)

    }

    /**
     * Shows the add favourite menu item and hides the remove favourite menu item
     */
    private fun showAddFavourite() {
        val add = menu.findItem(R.id.action_add_favourite)
        add.isVisible = true
        val remove = menu.findItem(R.id.action_remove_favourite)
        remove.isVisible = false
    }

    /**
     * Shows the remove favourite menu item and hides the add favourite menu item
     */
    private fun showRemoveFavourite() {
        val add = menu.findItem(R.id.action_add_favourite)
        add.isVisible = false
        val remove = menu.findItem(R.id.action_remove_favourite)
        remove.isVisible = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        this.menu = menu
        val storage = Storage(this)
        val favourites = storage.readListFromStorage(FAVOURITES_KEY, object: TypeToken<List<GameModel>>() {})
        if (favourites.contains(game)) {
            showRemoveFavourite()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add_favourite -> {
                Toast.makeText(this, resources.getText(R.string.added_to_favourites), Toast.LENGTH_SHORT).show()
                showRemoveFavourite()
                updateFavourites(game)
                true
            }

            R.id.action_remove_favourite -> {
                Toast.makeText(this, resources.getText(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show()
                showAddFavourite()
                updateFavourites(game)
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

    /**
     * Updates the expandable list adapter with listHeaders and listChildren
     *
     * Also removes any headers with empty children from the list
     */
    private fun updateListAdapter() {
        //This bit of magic removes any headers with empty children
        (0 until listHeaders.size)
                .filter { !listChildren.containsKey(listHeaders[it].name)
                        || listChildren[listHeaders[it].name]!!.isEmpty() }
                .map { listHeaders[it] }
                .forEach { listHeaders.remove(it) }

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
                bundle.putSerializable(RUN_EXTRA, run)
                bundle.putSerializable(GAME_EXTRA, game)
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
