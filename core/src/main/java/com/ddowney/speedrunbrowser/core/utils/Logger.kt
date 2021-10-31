package com.ddowney.speedrunbrowser.core.utils

import android.util.Log

public interface Logger {
  public fun d(tag: String, message: String)
  public fun d(tag: String, message: String, throwable: Throwable)
  public fun w(tag: String, message: String)
  public fun w(tag: String, message: String, throwable: Throwable)
  public fun e(tag: String, message: String)
  public fun e(tag: String, message: String, throwable: Throwable)
}

internal class DebugLogger : Logger {
  override fun d(tag: String, message: String) {
    Log.d(tag, message)
  }

  override fun d(tag: String, message: String, throwable: Throwable) {
    Log.d(tag, message, throwable)
  }

  override fun w(tag: String, message: String) {
    Log.w(tag, message)
  }

  override fun w(tag: String, message: String, throwable: Throwable) {
    Log.w(tag, message, throwable)
  }

  override fun e(tag: String, message: String) {
    Log.e(tag, message)
  }

  override fun e(tag: String, message: String, throwable: Throwable) {
    Log.e(tag, message, throwable)
  }
}

internal class StubLogger : Logger {
  override fun d(tag: String, message: String) {}

  override fun d(tag: String, message: String, throwable: Throwable) {}

  override fun w(tag: String, message: String) {}

  override fun w(tag: String, message: String, throwable: Throwable) {}

  override fun e(tag: String, message: String) {}

  override fun e(tag: String, message: String, throwable: Throwable) {}
}