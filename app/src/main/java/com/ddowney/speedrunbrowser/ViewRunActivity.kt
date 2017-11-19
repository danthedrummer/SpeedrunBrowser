package com.ddowney.speedrunbrowser

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatCallback
import android.support.v7.app.AppCompatDelegate
import android.support.v7.view.ActionMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_view_run.*
import kotlinx.android.synthetic.main.run_info_items.*
import java.net.URI

class ViewRunActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, AppCompatCallback {

    companion object {
        private val LOG_TAG = "ViewRunActivity"
        private val KEY = "AIzaSyBvMgY4_Su-zu_f7rluSewVzKd2s7buFMM"
        val RUN_EXTRA = "RUN_EXTRA"
        val RANDOM_RUN = "RANDOM_RUN"
    }

    //Sample data
    private val runnerName = "nindiddeh"
    private val runTime = 4480
    private val runComment = "Bad Pianta RNG, Bianco 6 death, and bad end game. Will keep trying for 1:13!"
    private val youtubeChannel = "https://www.youtube.com/channel/UCDOH__n9nq1r4vATGKKK6Iw"
    private val twitchChannel = "https://www.twitch.tv/nindiddeh"
    private val hitboxChannel = "https://www.smashcast.tv/"
    private val twitterChannel = "https://www.twitter.com/diddeh14"
    private val link = URI("https://www.youtube.com/watch?v=QwWSVON2gF4")

    private lateinit var youtubePlayer : YouTubePlayer
    private lateinit var delegate : AppCompatDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate = AppCompatDelegate.create(this, this)
        delegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        delegate.setContentView(R.layout.activity_view_run)
        delegate.setSupportActionBar(run_toolbar)

        //Not a string resource as it will be changing programmatically
        run_toolbar.title = "Any% - 1st place"

        youtube_player.initialize(KEY, this)

        vra_runner_name.text = runnerName
        vra_run_time.text = getReadableTime(runTime)
        vra_run_comment.text = runComment

        youtube_info_holder.visibility = View.VISIBLE
        vra_runner_youtube.text = youtubeChannel

        twitch_info_holder.visibility = View.VISIBLE
        vra_runner_twitch.text = twitchChannel

        twitter_info_holder.visibility = View.VISIBLE
        vra_runner_twitter.text = twitterChannel

        hitbox_info_holder.visibility = View.VISIBLE
        vra_runner_hitbox.text = hitboxChannel

        if (intent.extras.containsKey(RANDOM_RUN) && intent.extras.get(RANDOM_RUN) == true) {
            random_fab.visibility = View.VISIBLE
            random_fab.setOnClickListener({
                val runIntent = Intent(this, ViewRunActivity::class.java)
                runIntent.putExtra(RANDOM_RUN, true)
                startActivity(runIntent)
                finish()
            })
        }

    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, success: Boolean) {
        Log.d(LOG_TAG, "Player initialized successfully")
        youtubePlayer = player

        youtubePlayer.cueVideo(link.query.substring(2))
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
        Log.d(LOG_TAG, "Failed to initialize player. Reason: $errorReason")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        delegate.menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_favourite -> {
                Log.d(MainActivity.LOG_TAG, "Favourite clicked")
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
     * Takes a time in seconds and returns it in a readable format
     * e.g. 1 hour 14 minutes 40 seconds
     *
     * @param seconds The number of seconds
     * @return The readable time
     */
    private fun getReadableTime(seconds : Int) : String {
        val str = StringBuilder()
        var tmpNum = seconds
        var amount : Int

        if (tmpNum / 3600 != 0) {
            amount = tmpNum / 3600
            when (amount) {
                1 -> { str.append(" $amount hour") }
                else -> { str.append(" $amount hours") }
            }
            tmpNum %= 3600
        }

        if (tmpNum / 60 != 0) {
            amount = tmpNum / 60
            when (amount) {
                1 -> { str.append(" $amount minute") }
                else -> { str.append(" $amount minutes") }
            }
            tmpNum %= 60
        }

        when (tmpNum) {
            0 -> { }
            1 -> { str.append(" $tmpNum second") }
            else -> { str.append(" $tmpNum seconds") }
        }

        return str.toString()
    }
}
