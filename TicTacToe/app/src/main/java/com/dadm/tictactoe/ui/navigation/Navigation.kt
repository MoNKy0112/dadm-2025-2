package com.dadm.tictactoe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.screen.DifficultySelectionScreen
import com.dadm.tictactoe.ui.screen.GameScreen
import com.dadm.tictactoe.ui.screen.MainMenuScreen

@Composable
fun TicTacToeNavHost(
    navController: NavHostController,
    viewModel: TicTacToeViewModel
){
    NavHost(navController = navController, startDestination = "main_menu"){
        composable("main_menu"){
            MainMenuScreen(navController = navController, viewModel = viewModel)
        }
        composable("game"){
            GameScreen(navController = navController, viewModel = viewModel)
        }
        composable("difficulty_selection"){
            DifficultySelectionScreen(navController = navController, viewModel = viewModel)
        }
    }
}