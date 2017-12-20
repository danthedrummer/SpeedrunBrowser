package com.ddowney.speedrunbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatCallback
import android.support.v7.app.AppCompatDelegate
import android.support.v7.view.ActionMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ddowney.speedrunbrowser.ViewCategoriesActivity.Companion.GAME_EXTRA
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.Storage
import com.ddowney.speedrunbrowser.utils.FormattingTools
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_run.*
import kotlinx.android.synthetic.main.run_info_items.*

class ViewRunActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, AppCompatCallback {

    companion object {
        private val LOG_TAG = "ViewRunActivity"
        private val YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY
        val RUN_EXTRA = "RUN_EXTRA"
        val CATEGORY_NAME_EXTRA = "CATEGORY_NAME_EXTRA"
        val POSITION_EXTRA = "POSITION_EXTRA"
        val RANDOM_RUN_EXTRA = "RANDOM_RUN_EXTRA"
    }

    private lateinit var youtubePlayer : YouTubePlayer
    private lateinit var delegate : AppCompatDelegate
    private var playerInitResult : YouTubeInitializationResult? = null

    private lateinit var run : RunModel

    private lateinit var game : GameModel

    private lateinit var menu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        //Using AppCompatDelegate to enable a toolbar with menu options
        delegate = AppCompatDelegate.create(this, this)
        delegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        delegate.setContentView(R.layout.activity_view_run)
        delegate.setSupportActionBar(run_toolbar)

        val bundle = this.intent.extras
        if (bundle != null) {
            game = bundle.getSerializable(GAME_EXTRA) as GameModel
            run = bundle.getSerializable(RUN_EXTRA) as RunModel
            run_toolbar.title = game.names.international
        }

        if (run.players[0].rel == "user") {
            val userObservable = ServiceManager.userService.getUserById(run.players[0].id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            val userConsumer = Consumer<ResponseWrapperS<UserModel>> { (data) ->
                run_loading.visibility = View.GONE

                vra_runner_name.text = data.names.international
                runner_name_holder.visibility = View.VISIBLE

                vra_category_name.text = bundle.getString(CATEGORY_NAME_EXTRA)
                category_name_holder.visibility = View.VISIBLE

                val place = bundle.getInt(POSITION_EXTRA)
                vra_run_place.text = when (place) {
                    1 -> "${bundle.getInt(POSITION_EXTRA)}st"
                    2 -> "${bundle.getInt(POSITION_EXTRA)}nd"
                    3 -> "${bundle.getInt(POSITION_EXTRA)}rd"
                    else -> "${bundle.getInt(POSITION_EXTRA)}th"
                }
                run_place_holder.visibility = View.VISIBLE

                val formattingTool = FormattingTools()
                vra_run_time.text = formattingTool.getReadableTime(run.times.primary_t)
                time_holder.visibility = View.VISIBLE

                if (run.comment != null) {
                    vra_run_comment.text = run.comment
                    comment_holder.visibility = View.VISIBLE
                }

                if (data.youtube != null) {
                    val youtubeChannel = data.youtube.uri
                    vra_runner_youtube.text = youtubeChannel.toString()
                    youtube_info_holder.visibility = View.VISIBLE
                    youtube_info_holder.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeChannel.toString()))
                        startActivity(intent)
                    }
                }

                if (data.twitch != null) {
                    val twitchChannel = data.twitch.uri
                    vra_runner_twitch.text = twitchChannel.toString()
                    twitch_info_holder.visibility = View.VISIBLE
                    twitch_info_holder.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitchChannel.toString()))
                        startActivity(intent)
                    }
                }

                if (data.twitter != null) {
                    val twitterChannel = data.twitter.uri
                    vra_runner_twitter.text = twitterChannel.toString()
                    twitter_info_holder.visibility = View.VISIBLE
                    twitter_info_holder.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterChannel.toString()))
                        startActivity(intent)
                    }
                }

                if (data.hitbox != null) {
                    val hitboxChannel = data.hitbox.uri
                    vra_runner_hitbox.text = hitboxChannel.toString()
                    hitbox_info_holder.visibility = View.VISIBLE
                    hitbox_info_holder.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hitboxChannel.toString()))
                        startActivity(intent)
                    }
                }

                displayVideo()
            }
            userObservable.subscribe(userConsumer)

        } else {
            run_loading.visibility = View.GONE

            vra_runner_name.text = run.players[0].name
            runner_name_holder.visibility = View.VISIBLE

            vra_category_name.text = bundle.getString(CATEGORY_NAME_EXTRA)
            category_name_holder.visibility = View.VISIBLE

            val place = bundle.getInt(POSITION_EXTRA)
            vra_run_place.text = when (place) {
                1 -> "${bundle.getInt(POSITION_EXTRA)}st"
                2 -> "${bundle.getInt(POSITION_EXTRA)}nd"
                3 -> "${bundle.getInt(POSITION_EXTRA)}rd"
                else -> "${bundle.getInt(POSITION_EXTRA)}th"
            }
            run_place_holder.visibility = View.VISIBLE

            val formattingTool = FormattingTools()
            vra_run_time.text = formattingTool.getReadableTime(run.times.primary_t)
            time_holder.visibility = View.VISIBLE

            if (run.comment != null) {
                vra_run_comment.text = run.comment
                comment_holder.visibility = View.VISIBLE
            }

            displayVideo()
        }
    }

    /**
     * Attempts to display the video to the user using the embedded player.
     * If the video is not a youtube link, then the user is presented
     * with a button that directs them to the video
     */
    private fun displayVideo() {
        val link = run.videos.links[0].uri
        if (link.host.contains("youtube") || link.host.contains("youtu.be")) {
            youtube_player.initialize(YOUTUBE_API_KEY, this)
            youtube_player.visibility = View.VISIBLE
        } else {
            fallback_video_holder.visibility = View.VISIBLE
            fallback_video_button.setOnClickListener {
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
        val storage = Storage(this)
        val favourites = storage.readListFromStorage(Storage.FAVOURITES_KEY, object: TypeToken<List<GameModel>>() {})
        if (favourites.contains(game)) {
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add_favourite -> {
                Toast.makeText(this, "Added to favourites!", Toast.LENGTH_SHORT).show()
                showRemoveFavourite()
                updateFavourites(game)
                true
            }

            R.id.action_remove_favourite -> {
                Toast.makeText(this, "Removed from favourites!", Toast.LENGTH_SHORT).show()
                showAddFavourite()
                updateFavourites(game)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onWindowStartingSupportActionMode(callback: ActionMode.Callback?): ActionMode? { return null }

    override fun onSupportActionModeStarted(mode: ActionMode?) { }

    override fun onSupportActionModeFinished(mode: ActionMode?) { }

    /**
     * Extracts the youtube video id from a youtube url
     */
    private fun extractVideoLinkFromQuery(linkQuery : String) : String? {
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

}
