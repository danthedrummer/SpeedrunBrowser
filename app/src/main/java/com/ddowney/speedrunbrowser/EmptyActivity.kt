package com.ddowney.speedrunbrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * As it says on the tin. Just an empty activity for the app to
 * launch into for now. Will be removed later.
 */
class EmptyActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_empty)
  }
}