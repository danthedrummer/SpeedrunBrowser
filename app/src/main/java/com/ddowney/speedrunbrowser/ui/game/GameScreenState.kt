package com.ddowney.speedrunbrowser.ui.game

import com.ddowney.speedrunbrowser.core.model.Genre
import com.ddowney.speedrunbrowser.core.model.Platform
import com.ddowney.speedrunbrowser.ui.base.ViewState

sealed interface GameScreenState : ViewState {

  object Loading : GameScreenState

  data class Loaded(
    val id: String,
    val name: String,
    val releaseYear: Int,
    val platforms: List<Platform>,
    val tinyImageUri: String? = null,
    val genres: List<Genre>?,
  ) : GameScreenState
}