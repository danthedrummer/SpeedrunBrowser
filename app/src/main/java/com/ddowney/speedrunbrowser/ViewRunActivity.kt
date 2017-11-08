package com.ddowney.speedrunbrowser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ddowney.speedrunbrowser.models.ResponseWrapperM
import com.ddowney.speedrunbrowser.models.RunModel
import com.ddowney.speedrunbrowser.services.ServiceManager
import com.ddowney.speedrunbrowser.storage.TempDataStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_run.*

class ViewRunActivity : AppCompatActivity() {

    companion object {
        val GAME_ID_EXTRA = "GAME_ID_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_run)
        setSupportActionBar(run_toolbar)

        val gameId = intent.getStringExtra(GAME_ID_EXTRA)

        val runObservable = ServiceManager.runService.getRunsForGame(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val runConsumer = Consumer<ResponseWrapperM<RunModel>> { (data) ->
            run_game_name.text = TempDataStore.getGameNameById(data[0].game)
            run_runner_name.text = data[0].players[0].name ?: data[0].players[0].id
            run_time.text = data[0].times.primary
        }

        runObservable.subscribe(runConsumer)
    }
}
