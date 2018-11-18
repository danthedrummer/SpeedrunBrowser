package com.ddowney.speedrunbrowser

import android.app.Application
import com.ddowney.speedrunbrowser.injection.AppComponent
import com.ddowney.speedrunbrowser.injection.AppModule
import com.ddowney.speedrunbrowser.injection.DaggerAppComponent

class SpeedrunBrowser : Application() {

    val component: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()

        component.inject(this)
    }

}