package com.ddowney.speedrunbrowser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListAdapter
import com.ddowney.speedrunbrowser.MainActivity.Companion.LOG_TAG
import com.ddowney.speedrunbrowser.ViewRunActivity.Companion.RUN_EXTRA
import com.ddowney.speedrunbrowser.adapters.ExpandingCategoryListAdapter
import com.ddowney.speedrunbrowser.models.*
import com.ddowney.speedrunbrowser.services.ServiceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_game.*
import java.io.Serializable

class ViewLeaderboardsActivity : AppCompatActivity() {

    companion object {
        val GAME_ID_EXTRA = "GAME_ID_EXTRA"
        val GAME_EXTRA = "GAME_EXTRA"
    }

    private lateinit var expListAdapter : ExpandableListAdapter
    private lateinit var listHeaders: List<CategoriesModel>
    private lateinit var listChildren: Map<String, List<LeaderboardModel.RunPosition>>

    private var lastExpandedGroup = -1

    private val sampleGame : GameModel = GameModel("v1pxjz68",
            GameModel.GameNames("Super Mario Sunshine", "", "Super Mario Sunshine"),
            "sms", "", listOf("Gamecube"))

    // Load of sample shit to populate the list. Might be easier to actually just make requests
    private val sampleRunFirstCat1: RunModel = RunModel("", "",
            listOf(RunModel.PlayerModel("", "", "nindiddeh", "")),
            RunModel.TimeModel("PT1H14M40S", 4480))
    private val sampleRunSecondCat1: RunModel = RunModel("", "",
            listOf(RunModel.PlayerModel("", "", "Levaten", "")),
            RunModel.TimeModel("PT1H15M5S", 4505))
    private val sampleLeaderboardCat1: LeaderboardModel = LeaderboardModel("https://www.speedrun.com/sms#Any", "v1pxjz68",
            "n2y3r8do", "", "", "", "",
            listOf(LeaderboardModel.RunPosition(1, sampleRunFirstCat1),
                    LeaderboardModel.RunPosition(2, sampleRunSecondCat1)))

    private val sampleRunFirstCat2: RunModel = RunModel("", "",
            listOf(RunModel.PlayerModel("", "", "ShadowMario27", "")),
            RunModel.TimeModel("PT3H00M57S", 10857))
    private val sampleRunSecondCat2: RunModel = RunModel("", "",
            listOf(RunModel.PlayerModel("", "", "Yamata0921", "")),
            RunModel.TimeModel("PT3H1M18S", 10878))
    private val sampleLeaderboardCat2: LeaderboardModel = LeaderboardModel("https://www.speedrun.com/sms#Any", "v1pxjz68",
            "n2y3r8do", "", "", "", "",
            listOf(LeaderboardModel.RunPosition(1, sampleRunFirstCat2),
                    LeaderboardModel.RunPosition(2, sampleRunSecondCat2)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)
        setSupportActionBar(game_toolbar)

        game_toolbar.title = sampleGame.names.international

        val categoryObservable = ServiceManager.gameService.getCategoriesForGame(sampleGame.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val categoriesConsumer = Consumer<ResponseWrapperM<CategoriesModel>> { (data) ->

            val tempList = mutableListOf<CategoriesModel>()
            val tempMap = mutableMapOf<String, List<LeaderboardModel.RunPosition>>()
            data.forEach {
                tempList.add(it)
            }
            tempMap.put(tempList[0].name, sampleLeaderboardCat1.runs)
            tempMap.put(tempList[1].name, sampleLeaderboardCat2.runs)
            tempMap.put(tempList[2].name, sampleLeaderboardCat1.runs)
            tempMap.put(tempList[3].name, sampleLeaderboardCat2.runs)
            tempMap.put(tempList[4].name, sampleLeaderboardCat1.runs)
            tempMap.put(tempList[5].name, sampleLeaderboardCat2.runs)
            tempMap.put(tempList[6].name, sampleLeaderboardCat1.runs)
            tempMap.put(tempList[7].name, sampleLeaderboardCat2.runs)
            tempMap.put(tempList[8].name, sampleLeaderboardCat2.runs)
            listHeaders = tempList.toList()
            listChildren = tempMap.toMap()

            expListAdapter = ExpandingCategoryListAdapter(this, listHeaders, listChildren)

            expandable_category_list.setAdapter(expListAdapter)

            expandable_category_list.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                val intent = Intent(this, ViewRunActivity::class.java)
                val cat : String = listHeaders[groupPosition].name
                val run : RunModel? = listChildren[cat]?.get(childPosition)?.run
                if (run != null) {
                    intent.putExtra(RUN_EXTRA, run as Serializable)
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
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
