package com.jomoncaleanoa.tictactoemultiplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jomoncaleanoa.tictactoemultiplayer.ui.navigation.TicTacToeNavHost
import com.jomoncaleanoa.tictactoemultiplayer.ui.screens.LobbyScreen
import com.jomoncaleanoa.tictactoemultiplayer.ui.theme.TicTacToeMultiplayerTheme
import com.jomoncaleanoa.tictactoemultiplayer.utils.PreferencesManager
import com.jomoncaleanoa.tictactoemultiplayer.utils.SoundUseCase
import com.jomoncaleanoa.tictactoemultiplayer.viewModel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferencesManager(this)
        val viewModel = GameViewModel( context = this, prefs = prefs )
        enableEdgeToEdge()
        setContent {
            TicTacToeNavHost(navController = androidx.navigation.compose.rememberNavController(), gameViewModel = viewModel)
        }
    }
}