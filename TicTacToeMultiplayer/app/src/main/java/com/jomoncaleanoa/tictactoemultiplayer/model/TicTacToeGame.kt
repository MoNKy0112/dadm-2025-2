package com.jomoncaleanoa.tictactoemultiplayer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class Player { X, O, NONE }
enum class GameState { WAITING, PLAYING, X_WON, O_WON, DRAW, CANCELLED }
@Parcelize
data class TicTacToeGame(
    val board: List<List<Player>> = List(3) { List(3) { Player.NONE } },
    val currentPlayer: Player = Player.X,
    val gameState: GameState = GameState.PLAYING,
) : Parcelable
