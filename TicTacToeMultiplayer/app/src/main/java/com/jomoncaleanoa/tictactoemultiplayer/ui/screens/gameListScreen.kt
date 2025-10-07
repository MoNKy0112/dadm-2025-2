package com.jomoncaleanoa.tictactoemultiplayer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.tictactoemultiplayer.model.OnlineGame
import com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel

@Composable
fun GameListScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit,
    onJoin: (String) -> Unit
) {
    val games by viewModel.games.collectAsState()
    var filter by remember { mutableStateOf("") }

    // arrancar la escucha cuando se abre esta pantalla
    LaunchedEffect(Unit) {
        viewModel.listenGames()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack) {
                Text("Atrás")
            }
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedTextField(
                value = filter,
                onValueChange = { filter = it },
                label = { Text("Filtrar por ID") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Partidas disponibles", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if (games.isEmpty()) {
                Text("No hay partidas disponibles", modifier = Modifier.padding(12.dp))
            } else {
                games.forEach { (id, game) ->
                    if (filter.isBlank() || id.contains(filter, ignoreCase = true)) {
                        GameListItem(
                            id = id,
                            game = game,
                            onJoin = { onJoin(id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameListItem(id: String, game: OnlineGame, onJoin: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)
        .clickable { onJoin() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("ID: $id", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Anfitrión: ${game.playerX}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = onJoin) { Text("Unirse") }
            }
        }
    }
}
