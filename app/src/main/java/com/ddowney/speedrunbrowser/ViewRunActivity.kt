package com.ddowney.speedrunbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ActionMode
import com.ddowney.speedrunbrowser.ViewCategoriesActivity.Companion.GAME_EXTRA
import com.ddowney.speedrunbrowser.databinding.ActivityViewRunBinding
import com.ddowney.speedrunbrowser.databinding.RunInfoItemsBinding
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.networking.ErrorConsumer
import com.ddowney.speedrunbrowser.networking.UserProvider
import com.ddowney.speedrunbrowser.storage.SharedPreferencesStorage
import com.ddowney.speedrunbrowser.storage.Storage
import com.ddowney.speedrunbrowser.utils.TimeFormatter
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class ViewRunActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, AppCompatCallback {

  companion object {
    private const val LOG_TAG = "ViewRunActivity"
    private const val YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY
    const val RUN_EXTRA = "RUN_EXTRA"
    const val CATEGORY_NAME_EXTRA = "CATEGORY_NAME_EXTRA"
    const val POSITION_EXTRA = "POSITION_EXTRA"
    const val RANDOM_RUN_EXTRA = "RANDOM_RUN_EXTRA"
  }

  private lateinit var storage: Storage
  private lateinit var userProvider: UserProvider
  private val errorConsumer = ErrorConsumer()

  private lateinit var youtubePlayer: YouTubePlayer
  private lateinit var delegate: AppCompatDelegate
  private var playerInitResult: YouTubeInitializationResult? = null

  private lateinit var run: Run

  private lateinit var game: Game

  private lateinit var menu: Menu

  private val players = mutableListOf<User>()

  private lateinit var binding: ActivityViewRunBinding
  private lateinit var itemsBinding: RunInfoItemsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    val component = (this.application as SpeedrunBrowser).component
    userProvider = component.userProvider()
    storage = component.storage()

    binding = ActivityViewRunBinding.inflate(layoutInflater)
    itemsBinding = RunInfoItemsBinding.inflate(layoutInflater)

    //Using AppCompatDelegate to enable a toolbar with menu options
    delegate = AppCompatDelegate.create(this, this)
    delegate.onCreate(savedInstanceState)
    super.onCreate(savedInstanceState)
    delegate.setContentView(R.layout.activity_view_run)
    delegate.setSupportActionBar(binding.runToolbar)

    val bundle = this.intent.extras
    if (bundle != null) {
      game = bundle.getSerializable(GAME_EXTRA) as Game
      run = bundle.getSerializable(RUN_EXTRA) as Run
      binding.runToolbar.title = game.names.international
    }

    var responses = 0
    run.players.forEach { player ->
      if (player.rel == "guest") {
        players.add(User("", User.NamesModel(player.name, ""), player.uri,
          null, null, null, null, null))
        responses++
        if (responses == run.players.size && bundle != null) {
          displayUi(bundle)
        }
      } else {
        val userObservable = userProvider.getUser(player.id)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())

        val userConsumer = Consumer<ObjectRoot<User>> { (data) ->
          players.add(data)
          responses++
          if (responses == run.players.size && bundle != null) {
            displayUi(bundle)
          }
        }
        userObservable.subscribe(userConsumer, errorConsumer)
      }
    }

    if (responses > 1) {
      itemsBinding.vraRunnerNameLabel.text = getString(R.string.runners_label)
    }

  }

  private fun displayUi(bundle: Bundle) {
    binding.runLoading.visibility = View.GONE

    val runnerNames = StringBuilder()
    for (i in 0 until players.size) {
      runnerNames.append(players[i].names.international)
      if (i < players.size - 1) {
        runnerNames.append(", ")
      }
    }
    itemsBinding.vraRunnerName.text = runnerNames.toString()
    itemsBinding.runnerNameHolder.visibility = View.VISIBLE

    itemsBinding.vraCategoryName.text = bundle.getString(CATEGORY_NAME_EXTRA)
    itemsBinding.categoryNameHolder.visibility = View.VISIBLE

    val place = bundle.getInt(POSITION_EXTRA)
    itemsBinding.vraRunPlace.text = when (place) {
      1 -> "${bundle.getInt(POSITION_EXTRA)}st"
      2 -> "${bundle.getInt(POSITION_EXTRA)}nd"
      3 -> "${bundle.getInt(POSITION_EXTRA)}rd"
      else -> "${bundle.getInt(POSITION_EXTRA)}th"
    }
    itemsBinding.runPlaceHolder.visibility = View.VISIBLE

    val formattingTool = TimeFormatter()
    itemsBinding.vraRunTime.text = formattingTool.getReadableTime(run.times.primary_t)
    itemsBinding.timeHolder.visibility = View.VISIBLE

    if (run.comment != null) {
      itemsBinding.vraRunComment.text = run.comment
      itemsBinding.commentHolder.visibility = View.VISIBLE
    }

    displayVideo()
  }

  /**
   * Attempts to display the video to the user using the embedded player.
   * If the video is not a youtube link, then the user is presented
   * with a button that directs them to the video
   */
  private fun displayVideo() {
    //TODO: Implement solution for watching multiple part videos
    val link = run.videos.links[0].uri
    if (link.host.contains("youtube") || link.host.contains("youtu.be")) {
      binding.youtubePlayer.initialize(YOUTUBE_API_KEY, this)
      binding.youtubePlayer.visibility = View.VISIBLE
    } else {
      itemsBinding.fallbackVideoHolder.visibility = View.VISIBLE
      itemsBinding.fallbackVideoButton.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.toString()))
        startActivity(intent)
      }
    }
  }

  override fun onDestroy() {
    if (playerInitResult != null && playerInitResult == YouTubeInitializationResult.SUCCESS) {
      youtubePlayer.release()
    }
    super.onDestroy()
  }

  override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, success: Boolean) {
    Log.d(LOG_TAG, "Player initialized successfully")
    youtubePlayer = player

    val link = run.videos.links[0].uri
    if (link.host.contains("youtube")) {
      youtubePlayer.cueVideo(extractVideoLinkFromQuery(link.query))
    } else if (link.host.contains("youtu.be")) {
      youtubePlayer.cueVideo(link.path.substring(1))
    }

    playerInitResult = YouTubeInitializationResult.SUCCESS
  }

  override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
    Log.d(LOG_TAG, "Failed to initialize player. Reason: $errorReason")
    playerInitResult = errorReason
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    delegate.menuInflater.inflate(R.menu.game_menu, menu)
    this.menu = menu
    val storedFavourites = storage.get(SharedPreferencesStorage.FAVOURITES_KEY, Favourites::class.java)
    if (storedFavourites != null && storedFavourites.list.contains(game.id)) {
      showRemoveFavourite()
    }
    return true
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

  override fun onWindowStartingSupportActionMode(callback: ActionMode.Callback?): ActionMode? {
    return null
  }

  override fun onSupportActionModeStarted(mode: ActionMode?) {}

  override fun onSupportActionModeFinished(mode: ActionMode?) {}

  /**
   * Extracts the youtube video id from a youtube url
   */
  private fun extractVideoLinkFromQuery(linkQuery: String): String? {
    val queries = linkQuery.split("&")
    queries.forEach { query ->
      if (query.contains("v=")) {
        val split = query.indexOf("=")
        return query.substring(split + 1)
      }
    }
    return null
  }

  /**
   * Updates the user favourites by adding the game if it is not already
   * added to favourites or removing it if it is
   */
  private fun updateFavourites(game: Game): Int {
    val result: Int

    val storedFavourites = storage.get(SharedPreferencesStorage.FAVOURITES_KEY, Favourites::class.java)
    val updatedFavourites: MutableList<String> = storedFavourites?.list?.toMutableList() ?: mutableListOf()
    result = if (updatedFavourites.contains(game.id)) {
      updatedFavourites.remove(game.id)
      -1
    } else {
      updatedFavourites.add(game.id)
      1
    }

    storage.put(SharedPreferencesStorage.FAVOURITES_KEY, Favourites(updatedFavourites))
    return result
  }

}
