package com.dadm.tictactoe.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.screen.DifficultySelectionScreen
import com.dadm.tictactoe.ui.screen.GameScreen
import com.dadm.tictactoe.ui.screen.MainMenuScreen

@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel = viewModel()){
    val navController = rememberNavController()

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