package com.dadm.tictactoe.ViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.dadm.tictactoe.Model.GameState
import com.dadm.tictactoe.Model.Player
import com.dadm.tictactoe.Model.TicTacToeGame

enum class GameMode {
    SINGLE_PLAYER,
    TWO_PLAYER,
    MULTIPLAYER
}
class TicTacToeViewModel: ViewModel() {
    var game by mutableStateOf(TicTacToeGame())
        private set

    var gameMode by mutableStateOf(GameMode.TWO_PLAYER)
        private set

    fun setGameMode(mode: GameMode) {
        gameMode = mode
        resetGame()
    }

    fun makeMove(row: Int, col: Int) {
        println(row)
        println(col)
        if (game.gameState != GameState.PLAYING) return

        if (game.board[row][col] == Player.NONE) {
            val newBoard = game.board.mapIndexed { r, rows ->
                rows.mapIndexed { c, player ->
                    if (r == row && c == col) game.currentPlayer else player
                }
            }

            val newPlayer = if (game.currentPlayer == Player.X) Player.O else Player.X
            val newGameState = checkGameState(newBoard)

            game = game.copy(board = newBoard, currentPlayer = newPlayer, gameState = newGameState)
        }
    }

    private fun checkGameState(board: List<List<Player>>):GameState {
        val lines = board + board.indices.map {col -> board.map { it[col] } } + listOf(
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )

        if (lines.any {it.all { player -> player == Player.X } }) return GameState.X_WON
        if (lines.any {it.all { player -> player == Player.O } }) return GameState.O_WON
        if (board.all { it.all { player -> player != Player.NONE } }) return GameState.DRAW

        return GameState.PLAYING
    }

    fun resetGame() {
        game = TicTacToeGame()
    }

}

