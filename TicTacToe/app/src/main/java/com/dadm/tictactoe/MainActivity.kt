package com.dadm.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ViewModel.TicTacToeViewModelFactory
import com.dadm.tictactoe.ui.TicTacToeApp
import com.dadm.tictactoe.utils.SoundManager
import com.dadm.tictactoe.utils.SoundUseCase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val soundUseCase = SoundUseCase(this)

        val viewModel = ViewModelProvider(
            this,
            TicTacToeViewModelFactory(soundUseCase)
        )[TicTacToeViewModel::class.java]
        setContent {
            TicTacToeApp()
        }
    }
}
