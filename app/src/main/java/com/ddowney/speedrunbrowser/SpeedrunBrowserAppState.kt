package com.ddowney.speedrunbrowser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
  object Browse : Screen("browse")
  object Game : Screen("game/{gameId}") {
    fun createRoute(gameId: String) = "game/$gameId"
  }
}

@Composable
fun rememberAppState(
  navController: NavHostController = rememberNavController(),
) = remember(navController) {
  SpeedrunBrowserAppState(navController = navController)
}

class SpeedrunBrowserAppState(
  val navController: NavHostController,
) {

  fun navigateBack() {
    navController.popBackStack()
  }

  fun navigateToGame(gameId: String, from: NavBackStackEntry) {
    if (from.lifecycleIsResumed()) {
      navController.navigate(
        route = Screen.Game.createRoute(gameId = gameId),
      )
    }
  }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
  this.lifecycle.currentState == Lifecycle.State.RESUMED