package com.dadm.tictactoe.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(
    val title:String,
    val icon: ImageVector,
    val route: String? = null,
    val dialogType: DialogType? = null,
    val excludeFromRoutes: List<String> = listOf()
) {
    object MainMenu: BottomMenuScreen("Menu", Icons.Default.Home, "main_menu", excludeFromRoutes = listOf("main_menu"))
    object Game: BottomMenuScreen("Reiniciar", Icons.Default.PlayArrow, excludeFromRoutes = listOf("main_menu","difficulty_selection"))
    object DifficultySelection: BottomMenuScreen("Dificultad", Icons.Default.Settings, dialogType = DialogType.DIFFICULTY, excludeFromRoutes = listOf("main_menu","difficulty_selection"))
    object About: BottomMenuScreen("Acerca de", Icons.Default.Info, dialogType = DialogType.ABOUT)
    object Exit: BottomMenuScreen("Salir", Icons.Default.ExitToApp, dialogType = DialogType.EXIT)
}

enum class DialogType {
    DIFFICULTY, EXIT, ABOUT
}