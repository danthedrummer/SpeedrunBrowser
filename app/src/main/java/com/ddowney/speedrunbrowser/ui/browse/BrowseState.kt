package com.ddowney.speedrunbrowser.ui.browse

import com.ddowney.speedrunbrowser.core.model.Platform

internal data class BrowseState(
  val games: List<BrowseGame> = emptyList(),
)

internal data class BrowseGame(
  val id: String,
  val name: String,
  val releaseYear: Int,
  val platforms: List<Platform>,
  val tinyImageUri: String? = null,
)

