package com.ddowney.speedrunbrowser.networking

import android.util.Log
import io.reactivex.functions.Consumer

class ErrorConsumer : Consumer<Throwable> {

  companion object {
    private const val LOG_TAG = "NetworkingError"
  }

  override fun accept(t: Throwable?) {
    Log.e(LOG_TAG, "Something went wrong: ${t?.message}")
  }

}