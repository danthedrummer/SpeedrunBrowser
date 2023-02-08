package com.ddowney.speedrunbrowser.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SpeedrunBrowserTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colors = SpeedrunBrowserColors,
    typography = SpeedrunBrowserTypography,
    content = content,
  )
}