package com.ddowney.speedrunbrowser.ui.browse

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ddowney.speedrunbrowser.core.network.repository.GameRepository
import com.ddowney.speedrunbrowser.core.network.repository.PlatformRepository
import com.ddowney.speedrunbrowser.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BrowseViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val platformRepository: PlatformRepository,
) : BaseViewModel<BrowseEvent, BrowseState, BrowseSideEffect>() {

  init {
    viewModelScope.launch {
      // Warm up the platforms cache if needed
      platformRepository.fetchAllPlatforms()

      val games = gameRepository.getGames(
        options = mapOf("max" to "100"),
      )

      val newState = games.map { game ->

        val platforms = game.platforms?.map { platform ->
          platformRepository.getPlatform(platform.id)
        } ?: emptyList()

        BrowseGame(
          id = game.id,
          name = game.name.trim(),
          releaseYear = game.released.toString(),
          platforms = platforms.mapNotNull { it.name }.joinToString { it }.takeIf { it.isNotEmpty() } ?: "Unknown",
          tinyImageUri = game.assets?.coverTiny,
        )
      }

      setState { BrowseState.Loaded(games = newState) }
    }
  }

  override fun createInitialState(): BrowseState = BrowseState.Loading

  override fun handleEvent(event: BrowseEvent) {
    Log.d(TAG, "Received unhandled event - $event")
  }

  companion object {
    const val TAG = "BrowseViewModel"
  }
}