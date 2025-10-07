package com.jomoncaleanoa.tictactoemultiplayer.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jomoncaleanoa.tictactoemultiplayer.data.FirebaseRepository
import com.jomoncaleanoa.tictactoemultiplayer.model.GameState
import com.jomoncaleanoa.tictactoemultiplayer.model.OnlineGame
import com.jomoncaleanoa.tictactoemultiplayer.model.Player
import com.jomoncaleanoa.tictactoemultiplayer.utils.PreferencesManager
import com.jomoncaleanoa.tictactoemultiplayer.utils.SoundUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val context: Context,private val prefs: PreferencesManager? = null) : ViewModel() {
    var xWins by mutableStateOf(prefs?.getWins("X") ?: 0)
        private set

    var oWins by mutableStateOf(prefs?.getWins("O") ?: 0)
        private set

    var draws by mutableStateOf(prefs?.getWins("DRAW") ?: 0)
        private set
    private val repo = FirebaseRepository()
    private val soundUseCase = SoundUseCase(context)

    private val _games = MutableStateFlow<Map<String, OnlineGame>>(emptyMap())
    val games: StateFlow<Map<String, OnlineGame>> = _games

    private val _currentGame = MutableStateFlow<OnlineGame?>(null)
    val currentGame: StateFlow<OnlineGame?> = _currentGame

    var playerName: String = ""
    var localSymbol: String = "" // "X" or "O"
    var gameId: String? = null

    // control para que la UI sepa que debe volver al menú (ej: game cancelado por otro)
    private val _shouldExitToMenu = MutableStateFlow(false)
    val shouldExitToMenu: StateFlow<Boolean> = _shouldExitToMenu

    fun updatePlayerName(name: String) {
        playerName = name
    }

    fun createGame() {
        repo.createGame(playerName) { id ->
            gameId = id
            localSymbol = "X"
            listenGame(id)
        }
    }

    fun joinGame(id: String) {
        repo.joinGame(id, playerName) {
            gameId = id
            localSymbol = "O"
            listenGame(id)
        }
    }

    fun listenGames() {
        repo.listenForGames { map -> _games.value = map }
    }

    private fun listenGame(id: String) {
        repo.listenGame(id) { game ->
            _currentGame.value = game

            // si game fue cancelada por otro -> notificar a UI
            if (game.gameState == GameState.CANCELLED) {
                // forzar navegación al menú
                _shouldExitToMenu.value = true
            } else {
                _shouldExitToMenu.value = false
            }

            // si ambos pidieron revancha -> reset automático (serverless approach: client 1 or 2 will call reset; to avoid race, only let X reset)
            val rem = game.rematchRequests
            if (rem["X"] == true && rem["O"] == true) {
                // reset via repo (any client can call)
                repo.resetGame(gameId!!)
            }
        }
    }

    fun makeMove(row: Int, col: Int) {
        val current = _currentGame.value ?: return
        val id = gameId ?: return

        if (current.gameState != GameState.PLAYING) return
        if (current.currentPlayer != localSymbol) return
        if (current.board[row][col] != Player.NONE) return

        // sonido de movimiento
        soundUseCase.playMoveSound(if (localSymbol == "X") 1 else 2)

        val player = if (localSymbol == "X") Player.X else Player.O
        val newBoard = current.board.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                if (r == row && c == col) player else cell
            }
        }

        val newState = checkWinner(newBoard)
        val nextPlayer = if (current.currentPlayer == "X") "O" else "X"

        if (newState == GameState.X_WON || newState == GameState.O_WON) {
            soundUseCase.playWinSound()
        }

        val updated = current.copy(
            board = newBoard,
            currentPlayer = if (newState == GameState.PLAYING) nextPlayer else current.currentPlayer,
            gameState = newState
        )

        _currentGame.value = updated

        // persistir en Firebase (board como String names)
        repo.makeMove(
            id,
            newBoard.map { row -> row.map { it.name } },
            updated.currentPlayer,
            updated.gameState
        )
    }

    fun requestRematch() {
        val id = gameId ?: return
        val symbol = localSymbol
        // marcar voto true
        repo.setRematchVote(id, symbol, true)
    }

    fun cancelRematchVote() {
        val id = gameId ?: return
        val symbol = localSymbol
        repo.setRematchVote(id, symbol, false)
    }

    fun leaveGame() {
        val id = gameId ?: return
        repo.leaveGame(id, playerName)
        // local action: ask UI to go menu
        _shouldExitToMenu.value = true
    }

    // reiniciar localmente o pedir reinicio (not used if rematch flow used)
    fun forceReset() {
        gameId?.let { repo.resetGame(it) }
    }

    private fun checkWinner(board: List<List<Player>>): GameState {
        val lines = listOf(
            board[0], board[1], board[2],
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )
        return when {
            lines.any { it.all { p -> p == Player.X } } -> GameState.X_WON
            lines.any { it.all { p -> p == Player.O } } -> GameState.O_WON
            board.flatten().none { it == Player.NONE } -> GameState.DRAW
            else -> GameState.PLAYING
        }
    }
}
