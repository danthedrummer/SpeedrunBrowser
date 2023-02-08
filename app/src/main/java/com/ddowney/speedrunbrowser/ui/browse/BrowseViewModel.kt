package com.ddowney.speedrunbrowser.ui.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddowney.speedrunbrowser.core.network.repository.GameRepository
import com.ddowney.speedrunbrowser.core.network.repository.PlatformRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BrowseViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val platformRepository: PlatformRepository,
) : ViewModel() {

  private val _state = MutableStateFlow(BrowseState())
  val state: StateFlow<BrowseState> get() = _state

  init {
    viewModelScope.launch {
      // Warm up the platforms cache if needed
      platformRepository.fetchAllPlatforms()

      val games = gameRepository.getGames(
        options = mapOf("max" to "100"),
      )

      val newState = games.map {

        val platforms = it.platforms?.map { platformId ->
          platformRepository.getPlatform(platformId)
        } ?: emptyList()

        BrowseGame(
          id = it.id,
          name = it.name,
          releaseYear = it.released,
          platforms = platforms,
          tinyImageUri = it.assets?.coverTiny,
        )
      }

      _state.value = BrowseState(games = newState)
    }
  }
}
