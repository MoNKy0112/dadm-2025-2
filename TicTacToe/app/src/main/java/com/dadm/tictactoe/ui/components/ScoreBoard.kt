package com.dadm.tictactoe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.utils.PreferencesManager

@Composable
fun ScoreBoard(viewModel: TicTacToeViewModel) {
    Column {
        Text("Victorias X: ${viewModel.xWins}")
        Text("Victorias O: ${viewModel.oWins}")
        Text("Empates: ${viewModel.draws}")
    }
}

@Preview
@Composable
fun ScoreBoardPreview() {
    // Provide a mock or sample ViewModel for preview
    val context = LocalContext.current
    val prefs = PreferencesManager(context)
    val mockViewModel = TicTacToeViewModel(prefs = prefs, state = SavedStateHandle()).apply {
        // Set some sample data for preview
    }
    ScoreBoard(viewModel = mockViewModel)
}