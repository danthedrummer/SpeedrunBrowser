package com.ddowney.speedrunbrowser.ui.game

import com.ddowney.speedrunbrowser.core.model.Genre
import com.ddowney.speedrunbrowser.core.model.Platform

data class GameScreenState(
  val id: String,
  val name: String,
  val releaseYear: Int,
  val platforms: List<Platform>,
  val tinyImageUri: String? = null,
  val genres: List<Genre>?,
)