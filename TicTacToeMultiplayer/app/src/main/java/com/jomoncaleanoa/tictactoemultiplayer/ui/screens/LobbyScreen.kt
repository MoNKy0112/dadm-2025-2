package com.jomoncaleanoa.tictactoemultiplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel

@Composable
fun LobbyScreen(
    viewModel: GameViewModel,
    onOpenGameList: () -> Unit,
    onCreateGame: () -> Unit
) {
    var name by remember { mutableStateOf(viewModel.playerName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Triqui - Multijugador", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                viewModel.updatePlayerName(it) // si renombraste la función a updatePlayerName, cámbialo aquí
            },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onCreateGame,
            enabled = name.isNotBlank(),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Crear partida")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onOpenGameList,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Ver partidas disponibles")
        }
    }
}
