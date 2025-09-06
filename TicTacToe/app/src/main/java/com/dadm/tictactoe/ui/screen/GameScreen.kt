package com.dadm.tictactoe.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.Model.GameState
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.components.GameBoard
import com.dadm.tictactoe.ui.graphics.LoadGameImages

@Composable
fun GameScreen(navController: NavController, viewModel: TicTacToeViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val (xImage, oImage) = LoadGameImages()
        // button to return to the main menu
        GameBoard(viewModel = viewModel, xImage = xImage, oImage = oImage)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Turno del jugador: ${viewModel.game.currentPlayer}")
        Spacer(modifier = Modifier.height(16.dp))
        when (viewModel.game.gameState) {
            GameState.X_WON -> Text("¡Ganó el jugador X!")
            GameState.O_WON -> Text("¡Ganó el jugador O!")
            GameState.DRAW -> Text("¡Empate!")
            GameState.PLAYING -> {}
            else -> {}
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.resetGame()
            }) {
            Text("Reiniciar juego")
        }

        /*if (viewModel.gameMode == GameMode.SINGLE_PLAYER){
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
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(rememberNavController(), TicTacToeViewModel())
}