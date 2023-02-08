package com.ddowney.speedrunbrowser.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val DeepPurple500 = Color(0xFF673AB7)
val Amber500 = Color(0xFFFFC107)
val Red300 = Color(0xFFEA6D7E)

val SpeedrunBrowserColors = darkColors(
  primary = DeepPurple500,
  onPrimary = Color.White,
  primaryVariant = DeepPurple500,
  secondary = Amber500,
  onSecondary = Color.Black,
  error = Red300,
  onError = Color.Black
)