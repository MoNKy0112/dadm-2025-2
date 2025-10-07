package com.jomoncaleanoa.tictactoemultiplayer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel
import com.jomoncaleanoa.tictactoemultiplayer.utils.PreferencesManager

@Composable
fun ScoreBoard(viewModel: GameViewModel) {
    Column {
        Text("Victorias X: ${viewModel.xWins}")
        Text("Derrotas: ${viewModel.oWins}")
        Text("Empates: ${viewModel.draws}")
    }
}

@Preview
@Composable
fun ScoreBoardPreview() {
    // Provide a mock or sample ViewModel for preview
    val context = LocalContext.current
    val prefs = PreferencesManager(context)
//    val mockViewModel = TicTacToeViewModel(prefs = prefs, state = SavedStateHandle()).apply {
        // Set some sample data for preview
//    }
//    ScoreBoard(viewModel = mockViewModel)
}