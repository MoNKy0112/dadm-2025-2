package com.dadm.tictactoe.ui.components

import com.dadm.tictactoe.ui.graphics.TicTacToeColorPalette

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dadm.tictactoe.Model.GameState
import com.dadm.tictactoe.Model.Player
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun GameBoard(viewModel: TicTacToeViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){

        viewModel.game.board.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEachIndexed { colIndex, player ->
                    GameCell(player, onClick = {
                        viewModel.makeMove(rowIndex, colIndex)
                    })
                }
            }
        }


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
        Button(onClick = { viewModel.resetGame() }) {
            Text("Reiniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    GameBoard(TicTacToeViewModel())
}
