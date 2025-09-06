package com.dadm.tictactoe.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dadm.tictactoe.utils.SoundUseCase

class TicTacToeViewModelFactory(
    private val soundUseCase: SoundUseCase
) : ViewModelProvider.Factory { // Asegúrate de que extiende ViewModelProvider.Factory
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T { // Sobrescribe correctamente el método create
        return if (modelClass.isAssignableFrom(TicTacToeViewModel::class.java)) {
            TicTacToeViewModel(soundUseCase) as T // Crea el ViewModel con la dependencia
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
