package com.jomoncaleanoa.tictactoemultiplayer.utils


import android.content.Context;
import android.content.SharedPreferences;
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
            context.getSharedPreferences("tictactoe_prefs", Context.MODE_PRIVATE)

    fun saveWins(player: String, wins: Int) {
        prefs.edit { putInt("${player}_wins", wins) }
    }

    fun getWins(player: String): Int {
        return prefs.getInt("${player}_wins", 0)
    }

    fun resetWins() {
        prefs.edit { clear() }
    }
}
