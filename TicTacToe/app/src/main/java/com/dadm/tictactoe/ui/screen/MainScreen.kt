package com.dadm.tictactoe.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.GameMode

import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun MainMenuScreen(navController: NavController, viewModel: TicTacToeViewModel = viewModel()) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Text(text = "Selecciona el modo de juego:")

        GameModeButton(
            text = "Jugador vs Jugador",
            onClick = {
                viewModel.updateGameMode(GameMode.TWO_PLAYER)
                navController.navigate("game")
            }
        )

        GameModeButton(
            text = "Jugador vs CPU",
            onClick = {
                viewModel.updateGameMode(GameMode.SINGLE_PLAYER)
                navController.navigate("difficulty_selection")
            })

        GameModeButton(
            text = "Multijugador",
            buttonColors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            onClick = {

                Toast.makeText(context, "Multijugador no disponible", Toast.LENGTH_SHORT).show()
                // TODO: Implement Snackbar
                // viewModel.updateGameMode(GameMode.MULTIPLAYER)
                // navController.navigate("game")
            })




    }
}

@Composable
fun GameModeButton(text: String, onClick: () -> Unit, buttonColors: ButtonColors = ButtonDefaults.buttonColors()) {
    Button(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .widthIn(max = 240.dp)
            .fillMaxWidth()
            ,
        onClick = onClick,
        colors = buttonColors
    ) {
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen(navController = rememberNavController(), viewModel = TicTacToeViewModel()
    )
}
