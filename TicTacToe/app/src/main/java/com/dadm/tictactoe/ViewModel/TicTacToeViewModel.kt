package com.dadm.tictactoe.ViewModel


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dadm.tictactoe.Model.GameState
import com.dadm.tictactoe.Model.Player
import com.dadm.tictactoe.Model.TicTacToeGame
import com.dadm.tictactoe.utils.SoundUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.dadm.tictactoe.utils.PreferencesManager

enum class GameMode {
    SINGLE_PLAYER,
    TWO_PLAYER,
    MULTIPLAYER
}

enum class Difficulty {
    EASY, MEDIUM, IMPOSSIBLE, NONE
}

class TicTacToeViewModel(
    private val soundUseCase: SoundUseCase? = null,
    private val prefs: PreferencesManager? = null,
    private val state: SavedStateHandle? = null
) : ViewModel() {

    var xWins by mutableStateOf(prefs?.getWins("X") ?: 0)
        private set

    var oWins by mutableStateOf(prefs?.getWins("O") ?: 0)
        private set

    var draws by mutableStateOf(prefs?.getWins("DRAW") ?: 0)
        private set

    var game by mutableStateOf(
        state?.get<TicTacToeGame>("game") ?:
        TicTacToeGame()
    )
        private set


    var gameMode by mutableStateOf(GameMode.TWO_PLAYER)
        private set

    var difficulty by mutableStateOf(Difficulty.NONE)
        private set

    fun updateGameMode(mode: GameMode) {
        gameMode = mode
        resetGame()
    }

    fun updateDifficulty(diff: Difficulty) {
        difficulty = diff
        resetGame()
    }

    fun makeMove(row: Int, col: Int) {
        if (game.gameState != GameState.PLAYING) return
        if (gameMode == GameMode.SINGLE_PLAYER && game.currentPlayer == Player.O) return
        if (game.board[row][col] == Player.NONE) {
            updateBoard(row, col)

            if (gameMode == GameMode.SINGLE_PLAYER && game.gameState == GameState.PLAYING) {
                handleCpuTurn()
                soundUseCase?.playMoveSound(2)
            }
        }
    }

    private fun handleCpuTurn() {
        viewModelScope.launch {
            delay(500) // Añade un retraso de 1 segundo
            makeCpuMove() // Llama a la lógica para que la CPU haga su jugada
        }
    }

    private fun updateBoard(row: Int, col: Int) {
        val newBoard = game.board.mapIndexed { r, rows ->
            rows.mapIndexed { c, player ->
                if (r == row && c == col) game.currentPlayer else player
            }
        }

        if (game.currentPlayer == Player.X)
            soundUseCase?.playMoveSound(2)
        else
            soundUseCase?.playMoveSound(1)

        val newPlayer = if (game.currentPlayer == Player.X) Player.O else Player.X
        val newGameState = checkGameState(newBoard)


        if(newGameState == GameState.X_WON || (newGameState == GameState.O_WON && gameMode == GameMode.TWO_PLAYER)){
            print("Ganador: ${game.currentPlayer}")
            soundUseCase?.playWinSound()
        }

        val newGame = game.copy(board = newBoard, currentPlayer = newPlayer, gameState = newGameState)

        game = newGame
        state?.set("game", newGame)
        print(state)
    }

    private fun makeCpuMove() {
        val board = game.board


        if (difficulty == Difficulty.IMPOSSIBLE || difficulty == Difficulty.MEDIUM){
            // 1. Intentar ganar
            findMoveInLines(board, Player.O)?.let { (row, col) ->
                updateBoard(row, col)
                return
            }

            // 2. Bloquear al jugador
            findMoveInLines(board, Player.X)?.let { (row, col) ->
                updateBoard(row, col)
                return
            }
        }

        // 3. Estrategia (Centro y esquinas)
        if (difficulty == Difficulty.IMPOSSIBLE) {
            findStrategicMove()?.let { (row, col) ->
                updateBoard(row, col)
                return
            }
        }

        // 4. Movimiento aleatorio (último recurso)
        val emptyCells = board.flatMapIndexed { row, rows ->
            rows.mapIndexedNotNull { col, cell ->
                if (cell == Player.NONE) row to col else null
            }
        }

        if (emptyCells.isNotEmpty()) {
            val (row, col) = emptyCells.random()
            updateBoard(row, col)
        }
    }



    private fun findMoveInLines(board: List<List<Player>>, player: Player): Pair<Int, Int>? {
        // Genera las líneas como en checkGameState
        val lines = board +
                board.indices.map { col -> board.map { it[col] } } +
                listOf(
                    listOf(board[0][0], board[1][1], board[2][2]),
                    listOf(board[0][2], board[1][1], board[2][0])
                )

        // Itera sobre las líneas para encontrar una donde falte un movimiento para completar
        for (lineIndex in lines.indices) {
            val line = lines[lineIndex]
            if (line.count { it == player } == 2 && line.count { it == Player.NONE } == 1) {
                // Encuentra la celda vacía en la línea
                val emptyIndex = line.indexOf(Player.NONE)
                return mapLineToCell(lineIndex, emptyIndex)
            }
        }

        return null
    }

    private fun mapLineToCell(lineIndex: Int, emptyIndex: Int): Pair<Int, Int> {
        return when {
            lineIndex < 3 -> lineIndex to emptyIndex // Fila (0-2)
            lineIndex < 6 -> emptyIndex to (lineIndex - 3) // Columna (3-5)
            lineIndex == 6 -> emptyIndex to emptyIndex // Diagonal principal
            lineIndex == 7 -> emptyIndex to (2 - emptyIndex) // Diagonal secundaria
            else -> throw IllegalArgumentException("Invalid line index")
        }
    }

    private fun findStrategicMove(): Pair<Int, Int>? {
        val board = game.board
        // Prefiere el centro si está libre
        val center = board.size / 2
        if (board[center][center] == Player.NONE) {
            return center to center
        }

        // Prefiere las esquinas si están libres
        val corners = listOf(
            0 to 0,
            0 to board.size - 1,
            board.size - 1 to 0,
            board.size - 1 to board.size - 1
        )
        corners.forEach { (row, col) ->
            if (board[row][col] == Player.NONE) {
                return row to col
            }
        }

        return null
    }




    private fun checkGameState(board: List<List<Player>>):GameState {
        val lines = board + board.indices.map {col -> board.map { it[col] } } + listOf(
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )

        if (lines.any {it.all { player -> player == Player.X } }) {
            xWins++
            prefs?.saveWins("X", xWins)
            return GameState.X_WON
        }
        if (lines.any {it.all { player -> player == Player.O } }) {
            oWins++
            prefs?.saveWins("O", oWins)
            return GameState.O_WON
        }
        if (board.all { it.all { player -> player != Player.NONE } }) {
            draws++
            prefs?.saveWins("DRAW", draws)
            return GameState.DRAW
        }

        return GameState.PLAYING
    }

    fun resetGame() {
        game = TicTacToeGame()
    }

}

