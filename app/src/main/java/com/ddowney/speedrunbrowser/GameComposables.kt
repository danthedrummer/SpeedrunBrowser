package com.ddowney.speedrunbrowser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddowney.speedrunbrowser.core.network.responses.Game
import com.ddowney.speedrunbrowser.core.network.responses.GameNames

@Composable
fun GameItem(game: Game) {
  Column(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
  ) {
    Text(text = game.names.international, style = MaterialTheme.typography.h5)
    Text(text = "Release date: ${game.releaseDate.toString()}")
//    Text(text = game.platforms.toString())
  }
}

@Composable
fun GameList(games: List<Game>) {
  LazyColumn {
    items(
      items = games,
      itemContent = { game ->
          GameItem(game = game)
      }
    )
  }
}

@Composable
@Preview
fun DefaultGameList() {
  val games = listOf(
    game(name = "Elden Ring", platforms = listOf("Xbox", "PC"), release = 2022),
    game(name = "Elden Ring", platforms = listOf("Xbox", "PC"), release = 2022),
  )
  GameList(games = games)
}

fun game(
  name: String,
  platforms: List<String>,
  release: Int,
) = Game(
  id = "1234",
  names = GameNames(
    international = name,
    twitch = "",
    japanese = "",
  ),
  abbreviation = "",
  weblink = "",
  release = release,
  releaseDate = "",
  ruleSet = null,
  gameTypes = null,
  platforms = platforms,
  regions = null,
  genres = null,
  engines = null,
  developers = null,
  publishers = null,
  moderators = null,
  created = null,
  assets = null,
  pagination = null,
)