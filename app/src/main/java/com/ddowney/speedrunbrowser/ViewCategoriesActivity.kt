package com.ddowney.speedrunbrowser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.CATEGORY_NAME_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.POSITION_EXTRA
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RUN_EXTRA
import com.ddowney.speedrunbrowser.adapters.ExpandingCategoryListAdapter
import com.ddowney.speedrunbrowser.databinding.ActivityViewCategoryBinding
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.networking.*
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage.Companion.FAVOURITES_KEY
import com.ddowney.speedrunbrowser.storage.Storage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class ViewCategoriesActivity : AppCompatActivity() {

  companion object {
    val GAME_EXTRA = "GAME_EXTRA"
  }

  private lateinit var storage: Storage
  private lateinit var gameProvider: GameProvider
  private lateinit var categoriesProvider: CategoriesProvider
  private val errorConsumer = ErrorConsumer()

  private lateinit var game: Game

  private lateinit var expList: ExpandingCategoryListAdapter
  private lateinit var listHeaders: MutableList<Category>
  private var listChildren = mutableMapOf<String, List<Leaderboard.RunPosition>>()

  private var lastExpandedGroup = -1

  private lateinit var menu: Menu

  private lateinit var binding: ActivityViewCategoryBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    val component = (this.application as SpeedrunBrowser).component
    gameProvider = component.gameProvider()
    categoriesProvider = component.categoriesProvider()
    storage = component.storage()

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_category)
    setSupportActionBar(binding.gameToolbar)

    val bundle = this.intent.extras
    if (bundle != null) {
      game = bundle.getSerializable(GAME_EXTRA) as Game
    }

    binding.gameToolbar.title = game.names.international

    val categoryObservable = gameProvider.getCategoriesForGame(game.id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

    val categoriesConsumer = Consumer<ListRoot<Category>> { (data) ->
      listHeaders = data.toMutableList()
      var responses = 0

      for (i in 0 until data.size) {
        val recordsObserver = categoriesProvider.getRecordsForCategory(data[i].id, 5)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
        val recordsConsumer = Consumer<ListRoot<Leaderboard>> { record ->
          if (record.data.isEmpty()) {
            listHeaders.remove(data[i])
          } else {
            record.data.forEach {
              listChildren.put(data[i].name, it.runs)
            }
          }
          responses++
          if (responses == data.size) {
            updateListAdapter()
            binding.runLoading.visibility = View.GONE
            binding.expandableCategoryList.visibility = View.VISIBLE
          }
        }
        recordsObserver.subscribe(recordsConsumer, errorConsumer)
      }
    }

    categoryObservable.subscribe(categoriesConsumer, errorConsumer)

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
    val storedFavourites = storage.get(FAVOURITES_KEY, Favourites::class.java)
    if (storedFavourites != null && storedFavourites.list.contains(game.id)) {
      showRemoveFavourite()
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
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
  private fun updateFavourites(game: Game): Int {
    val result: Int

    val storedFavourites = storage.get(FAVOURITES_KEY, Favourites::class.java)
    val updatedFavourites: MutableList<String> = storedFavourites?.list?.toMutableList() ?: mutableListOf()
    result = if (updatedFavourites.contains(game.id)) {
      updatedFavourites.remove(game.id)
      -1
    } else {
      updatedFavourites.add(game.id)
      1
    }

    storage.put(FAVOURITES_KEY, Favourites(updatedFavourites))
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
      .filter {
        !listChildren.containsKey(listHeaders[it].name)
          || listChildren[listHeaders[it].name]!!.isEmpty()
      }
      .map { listHeaders[it] }
      .forEach { listHeaders.remove(it) }

    expList = ExpandingCategoryListAdapter(this, listHeaders, listChildren)
    binding.expandableCategoryList.setAdapter(expList)

    binding.expandableCategoryList.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
      val intent = Intent(this, ViewRunActivity::class.java)
      val bundle = Bundle()

      val cat: String = listHeaders[groupPosition].name
      bundle.putString(CATEGORY_NAME_EXTRA, cat)

      val pos: Int = childPosition + 1
      bundle.putInt(POSITION_EXTRA, pos)

      val run: Run? = listChildren[cat]?.get(childPosition)?.run
      if (run != null) {
        bundle.putSerializable(RUN_EXTRA, run)
        bundle.putSerializable(GAME_EXTRA, game)
        intent.putExtras(bundle)
        startActivity(intent)
      }
      true
    }

    binding.expandableCategoryList.setOnGroupExpandListener { groupPosition ->
      if (lastExpandedGroup != -1 && groupPosition != lastExpandedGroup) {
        binding.expandableCategoryList.collapseGroup(lastExpandedGroup)
      }
      lastExpandedGroup = groupPosition
    }
  }

}
