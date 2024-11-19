package com.dadm.tictactoe.ui.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.Difficulty
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun DifficultySelectionScreen(navController: NavController, viewModel: TicTacToeViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text ="Selecciona la dificultad:")

        DifficultyButton(
            text = "Fácil",
            onClick = {
                viewModel.updateDifficulty(Difficulty.EASY)
                navController.navigate("game")
            }
        )

        DifficultyButton(
            text = "Medio",
            onClick = {
                viewModel.updateDifficulty(Difficulty.MEDIUM)
                navController.navigate("game")
            }
        )

        DifficultyButton(
            text = "Nada es imposible... ¿?",
            onClick = {
                viewModel.updateDifficulty(Difficulty.IMPOSSIBLE)
                navController.navigate("game")
            }
        )

    }


}

@Composable
fun DifficultyButton(text: String, onClick: () -> Unit, buttonColors: ButtonColors = ButtonDefaults.buttonColors()) {
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
fun DifficultySelectionScreenPreview() {
    DifficultySelectionScreen(navController = rememberNavController(), viewModel = TicTacToeViewModel())
}