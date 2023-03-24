package com.ddowney.speedrunbrowser.ui.browse

import com.ddowney.speedrunbrowser.ui.base.ViewState

internal sealed interface BrowseState : ViewState {

  object Loading : BrowseState

  data class Loaded(
    val games: List<BrowseGame>,
  ) : BrowseState
}

internal data class BrowseGame(
  val id: String,
  val name: String,
  val releaseYear: String,
  val platforms: String,
  val tinyImageUri: String? = null,
)

