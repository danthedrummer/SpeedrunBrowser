package com.ddowney.speedrunbrowser.ui.browse

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.core.model.Platform
import com.ddowney.speedrunbrowser.theme.SpeedrunBrowserColors
import com.ddowney.speedrunbrowser.ui.SpeedrunBrowserTopBar

@Composable
internal fun Browse(
  viewModel: BrowseViewModel = hiltViewModel(),
  navigateToGame: (gameId: String) -> Unit,
) {
  Log.d("DanDebug", "Creating the browse composable")
  val viewState by viewModel.state.collectAsState()
  Surface(
    modifier = Modifier.fillMaxSize(),
  ) {
    BrowseContent(
      games = viewState.games,
      navigateToGame = navigateToGame,
    )
  }
}

@Composable
internal fun BrowseContent(
  games: List<BrowseGame>,
  navigateToGame: (gameId: String) -> Unit
) {
  Column {
    SpeedrunBrowserTopBar()
    GameList(
      games = games,
      navigateToGame = navigateToGame,
    )
  }
}

@Composable
internal fun GameItem(
  game: BrowseGame,
  navigateToGame: (gameId: String) -> Unit,
  modifier: Modifier,
) {
  ConstraintLayout(
    modifier = modifier.clickable { navigateToGame(game.id) },
  ) {
    val (divider, image, title, releaseYear, platforms) = createRefs()

    Divider(
      modifier = Modifier
        .fillMaxWidth()
        .constrainAs(divider) {
          top.linkTo(parent.top)
          centerHorizontallyTo(parent)
          width = fillToConstraints
        },
    )

    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(game.tinyImageUri)
        .crossfade(true)
        .build(),
      contentDescription = "Game logo",
      contentScale = ContentScale.Crop,
      placeholder = painterResource(id = R.drawable.blank_image),
      modifier = Modifier
        .width(64.dp)
        .height(64.dp)
        .clip(CircleShape)
        .border(1.dp, shape = CircleShape, color = SpeedrunBrowserColors.primary)
        .constrainAs(image) {
          start.linkTo(parent.start, 16.dp)
          top.linkTo(parent.top, 16.dp)
          bottom.linkTo(parent.bottom, 16.dp)
        },
    )

    Text(
      text = game.name.trim(),
      style = MaterialTheme.typography.h6,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      modifier = Modifier.constrainAs(title) {
        start.linkTo(image.end, 16.dp)
        end.linkTo(parent.end, 16.dp)
        top.linkTo(parent.top, 16.dp)
        width = fillToConstraints
      },
    )

    Text(
      text = "${game.releaseYear}",
      style = MaterialTheme.typography.subtitle1,
      modifier = Modifier.constrainAs(releaseYear) {
        start.linkTo(title.start)
        top.linkTo(title.bottom, 4.dp)
      },
      fontWeight = FontWeight.Bold,
    )

    Text(
      text = game.platforms.joinToString { it.name }.takeIf { it.isNotEmpty() } ?: "Unknown",
      style = MaterialTheme.typography.subtitle1,
      modifier = Modifier.constrainAs(platforms) {
        start.linkTo(releaseYear.end, 8.dp)
        top.linkTo(title.bottom, 4.dp)
      },
    )
  }
}

@Composable
internal fun GameList(
  games: List<BrowseGame>,
  navigateToGame: (gameId: String) -> Unit
) {
  LazyColumn {
    items(
      items = games,
      itemContent = { game ->
        GameItem(
          game = game,
          navigateToGame = navigateToGame,
          modifier = Modifier.fillParentMaxWidth(),
        )
      }
    )
  }
}

@Composable
@Preview
fun DefaultGameList() {
  val game = BrowseGame(
    id = "1234",
    name = "Elden Ring",
    releaseYear = 2022,
    platforms = listOf(
      Platform(
        id = "1234",
        name = "PC",
      )
    ),
  )
  GameList(
    games = listOf(game),
    navigateToGame = {},
  )
}