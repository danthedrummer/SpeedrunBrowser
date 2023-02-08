package com.ddowney.speedrunbrowser.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddowney.speedrunbrowser.core.network.repository.GameRepository
import com.ddowney.speedrunbrowser.core.network.repository.PlatformRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val platformRepository: PlatformRepository,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val gameId = savedStateHandle.get<String>("gameId") ?: error("Game ID should exist")

  var state by mutableStateOf<GameScreenState?>(null)
    private set

  init {
    viewModelScope.launch {
      val game = gameRepository.getGame(
        id = gameId,
        options = mapOf(
          "embed" to "genres",
        )
      )

      val platforms = game.platforms?.map { platformId ->
        platformRepository.getPlatform(platformId)
      } ?: emptyList()

      state = GameScreenState(
        id = game.id,
        name = game.name,
        releaseYear = game.released,
        platforms = platforms,
        tinyImageUri = game.assets?.coverTiny,
        genres = game.genres,
      )
    }
  }

}