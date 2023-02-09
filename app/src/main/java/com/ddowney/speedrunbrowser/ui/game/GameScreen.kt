package com.ddowney.speedrunbrowser.ui.game

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddowney.speedrunbrowser.R
import com.ddowney.speedrunbrowser.theme.SpeedrunBrowserColors
import com.ddowney.speedrunbrowser.ui.SpeedrunBrowserTopBar

@Composable
fun GameScreen(
  viewModel: GameScreenViewModel = hiltViewModel(),
  onBackPressed: () -> Unit,
) {
  val viewState by viewModel.state
  GameScreen(state = viewState, onBackPressed = onBackPressed)
}

@Composable
private fun GameScreen(
  state: GameScreenState,
  onBackPressed: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
  ) {
    when (state) {
      is GameScreenState.Loading -> {
        FullScreenLoading()
      }
      is GameScreenState.Loaded -> {
        GameScreenContent(
          state = state,
          onBackPressed = onBackPressed,
        )
      }
    }
  }
}

/**
 * Full screen circular progress indicator
 */
@Composable
private fun FullScreenLoading(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center)
  ) {
    CircularProgressIndicator()
  }
}

@Composable
private fun GameScreenContent(
  state: GameScreenState.Loaded,
  onBackPressed: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 8.dp),
  ) {
    SpeedrunBrowserTopBar(onBackPressed = onBackPressed)

    Spacer(modifier = Modifier.weight(1F))

    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(state.tinyImageUri)
        .crossfade(true)
        .build(),
      contentDescription = "Game logo",
      contentScale = ContentScale.Crop,
      placeholder = painterResource(id = R.drawable.blank_image),
      modifier = Modifier
        .clip(CircleShape)
        .border(1.dp, shape = CircleShape, color = SpeedrunBrowserColors.primary)
        .weight(10F),
    )

    Spacer(modifier = Modifier.weight(1F))

    Text(
      text = state.name.trim(),
      style = MaterialTheme.typography.h6,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      modifier = Modifier.weight(2F)
    )

    Spacer(modifier = Modifier.weight(1F))

    Text(
      text = state.genres?.map { it.name }?.takeIf { it.isNotEmpty() }?.joinToString()
        ?: "No genres",
      style = MaterialTheme.typography.h6,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      modifier = Modifier.weight(2F)
    )

    Spacer(modifier = Modifier.weight(1F))
  }
}