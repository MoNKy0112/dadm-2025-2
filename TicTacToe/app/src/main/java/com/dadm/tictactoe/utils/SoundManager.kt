package com.dadm.tictactoe.utils

import android.content.Context
import android.media.MediaPlayer

object SoundManager{
    private var mediaPlayer: MediaPlayer? = null

    // Función para cargar y reproducir sonido
    fun playSound(context:Context,soundResId: Int) {
        loadSound(context,soundResId)
        mediaPlayer?.start()
    }

    // Función privada para cargar el sonido
    private fun loadSound(context:Context, soundResId: Int) {
        // Si hay un sonido ya cargado, liberarlo antes de cargar uno nuevo
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundResId) // Cargar sonido desde recursos
    }

    // Función para detener y liberar recursos
    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
