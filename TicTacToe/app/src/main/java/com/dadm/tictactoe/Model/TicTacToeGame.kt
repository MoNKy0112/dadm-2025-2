package com.dadm.tictactoe.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class Player {
    X, O, NONE
}
enum class GameState {
    X_WON, O_WON, DRAW, PLAYING
}
@Parcelize
data class TicTacToeGame(
    val board: List<List<Player>> = List(3) { List(3) { Player.NONE } },
    val currentPlayer: Player = Player.X,
    val gameState: GameState = GameState.PLAYING,
) : Parcelable
