package com.ddowney.speedrunbrowser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import com.ddowney.speedrunbrowser.adapters.ExpandingCategoryListAdapter
import com.ddowney.speedrunbrowser.models.CategoriesModel
import com.ddowney.speedrunbrowser.models.GameModel
import com.ddowney.speedrunbrowser.models.ResponseWrapperM
import com.ddowney.speedrunbrowser.services.ServiceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_game.*

class ViewGameActivity : AppCompatActivity() {

    companion object {
        val GAME_ID_EXTRA = "GAME_ID_EXTRA"
        val GAME_EXTRA = "GAME_EXTRA"
    }

    private lateinit var expListAdapter : ExpandableListAdapter
    private lateinit var listDataHeaders : List<String>
    private lateinit var listDataChildren : Map<String, List<String>>

    private var lastExpandedGroup = -1

    private val sampleGame : GameModel = GameModel("v1pxjz68",
            GameModel.GameNames("Super Mario Sunshine", "", "Super Mario Sunshine"),
            "sms", "", listOf("Gamecube"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)
        setSupportActionBar(game_toolbar)

        game_toolbar.title = sampleGame.names.international

        val categoryObservable = ServiceManager.gameService.getCategoriesForGame(sampleGame.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


        val categoriesConsumer = Consumer<ResponseWrapperM<CategoriesModel>> { (data) ->

            val tempList = mutableListOf<String>()
            val tempMap = mutableMapOf<String, List<String>>()
            data.forEach {
                tempList.add(it.name)
                tempMap.put(it.name, listOf(it.type, it.type, it.type, it.type, it.type))
            }
            listDataHeaders = tempList.toList()
            listDataChildren = tempMap.toMap()

            expListAdapter = ExpandingCategoryListAdapter(this, listDataHeaders, listDataChildren)

            expandable_category_list.setAdapter(expListAdapter)

            expandable_category_list.setOnChildClickListener { parent, view, groupPosition, childPosition, id ->
                Log.d("wubalub", listDataChildren[listDataHeaders[groupPosition]]?.get(childPosition))
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


//        val gameId = intent.getStringExtra(GAME_ID_EXTRA)
//
//        val runObservable = ServiceManager.runService.getRunsForGame(gameId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//        val runConsumer = Consumer<ResponseWrapperM<RunModel>> { (data) ->
//            //Do something
//        }
//
//        runObservable.subscribe(runConsumer)
    }
}
