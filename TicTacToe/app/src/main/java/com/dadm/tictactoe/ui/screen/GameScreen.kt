package com.dadm.tictactoe.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.dadm.tictactoe.ui.components.ScoreBoard
import com.dadm.tictactoe.ui.graphics.LoadGameImages

@Composable
fun GameScreen(navController: NavController, viewModel: TicTacToeViewModel) {
    val (xImage, oImage) = LoadGameImages()

    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val isPortrait = this.maxHeight > this.maxWidth
        if (isPortrait){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {


                // button to return to the main menu
                GameBoard(viewModel = viewModel, xImage = xImage, oImage = oImage, modifier = Modifier.fillMaxWidth(0.9f))
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.weight(1f), // empuja el contenido hacia abajo
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Turno del jugador: ${viewModel.game.currentPlayer}")
                    Spacer(modifier = Modifier.height(8.dp))
                    when (viewModel.game.gameState) {
                        GameState.X_WON -> Text("¡Ganó el jugador X!")
                        GameState.O_WON -> Text("¡Ganó el jugador O!")
                        GameState.DRAW -> Text("¡Empate!")
                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ScoreBoard(viewModel= viewModel)
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
            }
        }else{
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tablero a la izquierda
                GameBoard(
                    viewModel = viewModel,
                    xImage = xImage,
                    oImage = oImage,
                    modifier = Modifier
                        .weight(1f) // que ocupe espacio proporcionado
                        .padding(end = 16.dp)
                )

                // Controles a la derecha
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween // reparte arriba y abajo
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Turno del jugador: ${viewModel.game.currentPlayer}")
                        Spacer(modifier = Modifier.height(8.dp))
                        when (viewModel.game.gameState) {
                            GameState.X_WON -> Text("¡Ganó el jugador X!")
                            GameState.O_WON -> Text("¡Ganó el jugador O!")
                            GameState.DRAW -> Text("¡Empate!")
                            else -> {}
                        }
                    }

                    ScoreBoard(viewModel= viewModel)

                    Button(
                        onClick = { viewModel.resetGame() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reiniciar juego")
                    }
                }
            }
        }

    }
}

fun Modifier.Companion.weight(f: Float) {}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen(rememberNavController(), TicTacToeViewModel())
}