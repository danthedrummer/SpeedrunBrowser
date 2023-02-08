package com.ddowney.speedrunbrowser

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ddowney.speedrunbrowser.ui.browse.Browse
import com.ddowney.speedrunbrowser.ui.game.GameScreen

@Composable
fun SpeedrunBrowserApp(
  appState: SpeedrunBrowserAppState = rememberAppState(),
) {
  NavHost(
    navController = appState.navController,
    startDestination = Screen.Browse.route,
  ) {

    composable(route = Screen.Browse.route) { backStackEntry ->
      Browse(
        navigateToGame = { gameId ->
          appState.navigateToGame(
            gameId = gameId,
            from = backStackEntry,
          )
        }
      )
    }

    composable(route = Screen.Game.route) { backStackEntry ->
      GameScreen(
        onBackPressed = appState::navigateBack
      )
    }
  }
}