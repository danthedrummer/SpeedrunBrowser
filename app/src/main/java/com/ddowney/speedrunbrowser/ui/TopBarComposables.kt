package com.ddowney.speedrunbrowser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ddowney.speedrunbrowser.R

@Composable
fun SpeedrunBrowserTopBar(
  onBackPressed: (() -> Unit)? = null
) {
  TopAppBar(
    modifier = Modifier.height(64.dp),
    elevation = 8.dp,
  ) {
    Row(Modifier.fillMaxWidth()) {
      if (onBackPressed != null) {
        IconButton(onClick = onBackPressed,) {
          Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go back",
          )
        }
      }
      Spacer(Modifier.weight(1f))
      Image(
        painter = painterResource(id = R.drawable.ic_app_icon),
        contentDescription = "Speedrun Browser",
        modifier = Modifier.size(64.dp)
          .padding(8.dp),
      )
    }
  }
}