package com.ddowney.speedrunbrowser

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ddowney.speedrunbrowser.theme.SpeedrunBrowserTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * As it says on the tin. Just an empty activity for the app to
 * launch into for now. Will be removed later.
 */
@AndroidEntryPoint
class EmptyActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("DanDebug", "Launched empty activity")
    setContent {
      SpeedrunBrowserTheme {
        SpeedrunBrowserApp()
      }
    }
  }
}