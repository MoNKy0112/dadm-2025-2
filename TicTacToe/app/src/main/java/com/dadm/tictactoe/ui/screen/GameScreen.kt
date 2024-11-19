package com.dadm.tictactoe.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.GameMode
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.components.GameBoard

@Composable
fun GameScreen(navController: NavController, viewModel: TicTacToeViewModel) {
    Column {
        // button to return to the main menu
        GameBoard(viewModel = viewModel)
        if (viewModel.gameMode == GameMode.SINGLE_PLAYER){
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate("difficulty_selection")
                }) {
                Text("Cambiar dificultad")
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                navController.navigate("main_menu")
            }) {
            Text("Regresar al menú principal")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(rememberNavController(), TicTacToeViewModel())
}