package com.dadm.tictactoe.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun MainScreen(viewModel: TicTacToeViewModel = viewModel()) {
    GameBoard(viewModel = viewModel)
}