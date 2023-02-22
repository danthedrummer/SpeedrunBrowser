package com.ddowney.speedrunbrowser.ui.game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddowney.speedrunbrowser.core.network.repository.GameRepository
import com.ddowney.speedrunbrowser.core.network.repository.PlatformRepository
import com.ddowney.speedrunbrowser.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val platformRepository: PlatformRepository,
  savedStateHandle: SavedStateHandle,
) : BaseViewModel<GameScreenEvent, GameScreenState, GameScreenSideEffect>() {

  private val gameId = savedStateHandle.get<String>("gameId") ?: error("Game ID should exist")

  init {
    viewModelScope.launch {
      val game = gameRepository.getGame(
        id = gameId,
        options = mapOf(
          "embed" to "genres,platforms",
        )
      )

      val platforms = game.platforms?.map { platform ->
        platformRepository.getPlatform(platform.id)
      } ?: emptyList()

      setState {
        GameScreenState.Loaded(
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

  override fun createInitialState(): GameScreenState = GameScreenState.Loading

  override fun handleEvent(event: GameScreenEvent) {
    Log.d(TAG, "Received unhandled event - $event")
  }

  companion object {
    private const val TAG = "GameScreenVM"
  }
}