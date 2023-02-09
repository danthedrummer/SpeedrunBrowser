package com.ddowney.speedrunbrowser.ui.browse

import com.ddowney.speedrunbrowser.core.model.Platform
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
  val releaseYear: Int,
  val platforms: List<Platform>,
  val tinyImageUri: String? = null,
)

