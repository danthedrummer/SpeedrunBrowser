package com.ddowney.speedrunbrowser.feature.browser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.ddowney.speedrunbrowser.models.Game
import com.ddowney.speedrunbrowser.networking.GameProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class BrowserActivity : ComponentActivity() {

  companion object {
    private const val LOG_TAG = "BrowserActivity"
  }

  @Inject
  lateinit var gameProvider: GameProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch(Dispatchers.IO) {
      val result = gameProvider.coGetGames()
      Log.d(LOG_TAG, result.data.toString())
      withContext(Dispatchers.Main) {
        setContent {
          BrowserList(games = result.data)
        }
      }
    }
  }
}

@Composable
fun BrowserList(games: List<Game>) {
  MaterialTheme {
//    CircularProgressIndicator(
//      modifier = Modifier
//        .width(32.dp)
//        .height(32.dp)
//    )
    GameList(games = games)
  }
}

@Preview
@Composable
fun PreviewBrowserList() {
  BrowserList(emptyList())
}

@Composable
fun GameList(games: List<Game>) {
  LazyColumn(modifier = Modifier.padding(8.dp)) {
    items(games) { game ->
      GameRow(game = game)
    }
  }
}

@Composable
fun GameRow(game: Game) {
  Row(
    modifier = Modifier
      .padding(8.dp)
      .fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.padding(
        start = 8.dp,
        end = 8.dp,
      ),
    ) {
      Text(
        text = game.names.international,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h6,
      )
      Text(
        text = game.platforms?.joinToString(", ") ?: "Platforms unknown",
        style = MaterialTheme.typography.body2,
      )
    }
  }
}
