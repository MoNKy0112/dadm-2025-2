package com.jomoncaleanoa.tictactoemultiplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jomoncaleanoa.tictactoemultiplayer.ui.screens.GameListScreen
import com.jomoncaleanoa.tictactoemultiplayer.ui.screens.GameScreen
import com.jomoncaleanoa.tictactoemultiplayer.ui.screens.LobbyScreen

@Composable
fun TicTacToeNavHost(
    navController: androidx.navigation.NavHostController,
    gameViewModel: com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            LobbyScreen(
                viewModel = gameViewModel,
                onOpenGameList = { navController.navigate("list") },
                onCreateGame = {
                    gameViewModel.createGame()
                    navController.navigate("game")
                }
            )
        }

        composable("list") {
            GameListScreen(
                viewModel = gameViewModel,
                onBack = { navController.popBackStack() },
                onJoin = { id ->
                    gameViewModel.joinGame(id)
                    navController.navigate("game")
                }
            )
        }

        composable("game") {
            GameScreen(
                viewModel = gameViewModel,
                onBackToMenu = {
                    navController.popBackStack("menu", inclusive = false)
                }
            )
        }
    }
}