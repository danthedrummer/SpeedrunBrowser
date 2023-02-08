package com.ddowney.speedrunbrowser.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.ddowney.speedrunbrowser.R

val Poppins = FontFamily(
  Font(R.font.poppins_light, FontWeight.Light),
  Font(R.font.poppins_regular, FontWeight.Normal),
  Font(R.font.poppins_medium, FontWeight.Medium),
  Font(R.font.poppins_semibold, FontWeight.SemiBold),
)

val SpeedrunBrowserTypography = Typography(
  defaultFontFamily = Poppins,
)