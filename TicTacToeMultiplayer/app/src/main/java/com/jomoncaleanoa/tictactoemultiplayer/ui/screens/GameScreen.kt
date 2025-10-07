package com.jomoncaleanoa.tictactoemultiplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jomoncaleanoa.tictactoemultiplayer.model.GameState
import com.jomoncaleanoa.tictactoemultiplayer.ui.components.GameBoard
import com.jomoncaleanoa.tictactoemultiplayer.ui.components.GameOverlay
import com.jomoncaleanoa.tictactoemultiplayer.ui.components.ScoreBoard
import com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel,
               onBackToMenu: () -> Unit
) {
    var onMenuClick = {
        viewModel.leaveGame()
        onBackToMenu()
    }

    var onRematchClick ={
        viewModel.requestRematch()
    }

    val game by viewModel.currentGame.collectAsState()
    val shouldExit by viewModel.shouldExitToMenu.collectAsState()
    // navega fuera si el viewModel indica que la partida fue cancelada
    LaunchedEffect(shouldExit) {
        if (shouldExit) onBackToMenu()
    }
    game?.let{currentGame ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val isPortrait = this.maxHeight > this.maxWidth
            if (isPortrait){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {

                    // header: show turn
                    val current = game
                    val localSymbol = viewModel.localSymbol
                    val opponentName = when {
                        current == null -> ""
                        viewModel.localSymbol == "X" -> current.playerO ?: "Esperando..."
                        else -> current.playerX
                    }

                    val turnText = remember(current, localSymbol) {
                        if (current == null) ""
                        else if (current.gameState == GameState.WAITING) "Esperando jugador..."
                        else if (current.currentPlayer == localSymbol) "Tu turno"
                        else "Turno de: ${opponentName ?: "oponente"}"
                    }
                    Text(text = "Jugador: ${viewModel.playerName}")
                    Spacer(Modifier.height(6.dp))
                    Text(text = turnText, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(12.dp))

                    // Game board area - we restrict width so it doesn't take whole screen
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        current?.let { g ->
                            GameBoard(
                                board = g.board,
                                onCellClick = { r, c -> viewModel.makeMove(r, c) },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f) // 90% of width
                            )
                        } ?: run {
                            Text("Cargando partida...")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.weight(1f), // empuja el contenido hacia abajo
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (currentGame.gameState) {
                            GameState.PLAYING -> { /* nothing special */ }
                            GameState.WAITING -> GameOverlay(true, "Esperando a otro jugador...", false, onMenuClick, {})
                            GameState.X_WON -> GameOverlay(false, "¡Ganó ${currentGame.playerX}!", true, onMenuClick, onRematchClick)
                            GameState.O_WON -> GameOverlay(false, "¡Ganó ${currentGame.playerO}!", true, onMenuClick, onRematchClick)
                            GameState.DRAW -> GameOverlay(false, "¡Empate!", true, onMenuClick, onRematchClick)
                            else -> { /* cancelled handled by nav effect */ }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ScoreBoard(viewModel= viewModel)
                    // bottom buttons (menu + reset/revancha)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = {
                                viewModel.leaveGame()
                                onBackToMenu()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Menu")
                        }

                        Button(
                            onClick = { viewModel.requestRematch() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Revancha")
                        }
                    }
                }
            }else{
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tablero a la izquierda
                    GameBoard(
                        board = currentGame.board,
                        onCellClick = { row, col -> viewModel.makeMove(row, col) },
                        modifier = Modifier.fillMaxWidth(0.9f)
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
                            Text("Turno del jugador: ${currentGame.currentPlayer}")
                            Spacer(modifier = Modifier.height(8.dp))
                            when (currentGame.gameState) {
                                GameState.X_WON -> Text("¡Ganó el jugador X!")
                                GameState.O_WON -> Text("¡Ganó el jugador O!")
                                GameState.DRAW -> Text("¡Empate!")
                                else -> {}
                            }
                        }

//                        ScoreBoard(viewModel= viewModel)
//
//                        Button(
//                            onClick = { viewModel.resetGame() },
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Text("Reiniciar juego")
//                        }
                    }
                }
            }
            // Overlays: waiting / finished - modal in center with blur behind
            currentGame?.let { g ->
                val showWaiting = g.gameState == GameState.WAITING
                val showFinished = g.gameState == GameState.X_WON || g.gameState == GameState.O_WON || g.gameState == GameState.DRAW

                if (showWaiting || showFinished) {
                    // background blur + dark overlay
                    Box(
                        Modifier
                            .fillMaxSize()
                            .blur(12.dp) // requires compose-ui >= 1.4.0; if not available, replace with semi-transparent background
                            .background(Color.Black.copy(alpha = 0.35f))
                    ) {}

                    // modal card
                    Box(
                        Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.widthIn(max = 340.dp)) {
                            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                if (showWaiting) {
                                    Text("Esperando a otro jugador...", style = MaterialTheme.typography.titleLarge)
                                    Spacer(Modifier.height(8.dp))
                                    Text("Comparte el código de la partida o espera a que alguien se una.")
                                } else {
                                    // finished
                                    val message = when (g.gameState) {
                                        GameState.X_WON -> "¡Ganó ${g.playerX}!"
                                        GameState.O_WON -> "¡Ganó ${g.playerO ?: "O"}!"
                                        GameState.DRAW -> "¡Empate!"
                                        else -> ""
                                    }
                                    Text(message, style = MaterialTheme.typography.titleLarge)
                                    Spacer(Modifier.height(8.dp))

                                    // show rematch vote status
                                    val xVote = g.rematchRequests["X"] == true
                                    val oVote = g.rematchRequests["O"] == true
                                    Text("Revancha: X -> ${if (xVote) "✓" else "—"}  O -> ${if (oVote) "✓" else "—"}")
                                }

                                Spacer(Modifier.height(16.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(onClick = {
                                        // leave and go to menu
                                        viewModel.leaveGame()
                                        onBackToMenu()
                                    }) {
                                        Text("Menu")
                                    }
                                    OutlinedButton(onClick = {
                                        // toggle request rematch
                                        viewModel.requestRematch()
                                    }) {
                                        Text("Revancha")
                                    }
                                }
                            }
                        }
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
//    GameScreen(rememberNavController(), TicTacToeViewModel())
}