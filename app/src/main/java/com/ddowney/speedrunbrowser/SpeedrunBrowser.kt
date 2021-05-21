package com.ddowney.speedrunbrowser

import android.app.Application
import com.ddowney.speedrunbrowser.injection.*

class SpeedrunBrowser : Application() {

  val component: AppComponent = DaggerAppComponent
    .builder()
    .appModule(AppModule(this))
    .networkModule(NetworkModule())
    .storageModule(StorageModule(this))
    .build()

  override fun onCreate() {
    super.onCreate()

    component.inject(this)
  }

}