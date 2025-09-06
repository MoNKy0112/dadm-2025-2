package com.dadm.tictactoe.utils

import com.dadm.tictactoe.R
import android.content.Context
class SoundUseCase(private val context: Context) {

    fun playMoveSound(player: Int) {
        if (player == 1) SoundManager.playSound(context, R.raw.player1_play_sound)
        else if (player == 2) SoundManager.playSound(context, R.raw.player2_play_sound)

    }

    fun playWinSound() {
        SoundManager.playSound(context, R.raw.win_sound)
    }

    // Puedes agregar más métodos si necesitas otros sonidos
}
