package com.dadm.tictactoe.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.components.BottomNavMenu
import com.dadm.tictactoe.ui.navigation.TicTacToeNavHost

@Composable
fun TicTacToeApp(viewModel: TicTacToeViewModel = viewModel()){
    val navController = rememberNavController()
    //TicTacToeNavHost(navController = navController, viewModel = viewModel)

    Scaffold (
        bottomBar = {
            BottomNavMenu(navController = navController, viewModel = viewModel, context = LocalContext.current)
        }
    ){innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            TicTacToeNavHost(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    TicTacToeApp()
}