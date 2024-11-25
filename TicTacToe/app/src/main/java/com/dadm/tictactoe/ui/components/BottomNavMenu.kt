package com.dadm.tictactoe.ui.components


import android.app.Activity
import android.content.Context
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dadm.tictactoe.ViewModel.GameMode
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel
import com.dadm.tictactoe.ui.navigation.BottomMenuScreen
import com.dadm.tictactoe.ui.navigation.DialogType

@Composable
fun BottomNavMenu(navController: NavController, viewModel: TicTacToeViewModel, context: Context) {
    var currentDialog by remember { mutableStateOf<DialogType?>(null) }

    val items = listOf(
        BottomMenuScreen.MainMenu,
        BottomMenuScreen.Game,
        BottomMenuScreen.DifficultySelection,
        BottomMenuScreen.About,
        BottomMenuScreen.Exit
    )

    BottomAppBar{
        val currentDestination =
            navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            if (screen.excludeFromRoutes.contains(currentDestination)) return@forEach
            if (screen == BottomMenuScreen.DifficultySelection && viewModel.gameMode != GameMode.SINGLE_PLAYER) return@forEach
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                },
                label = { Text(screen.title) },
                selected = currentDestination == screen.route,
                onClick = {
                    if (screen == BottomMenuScreen.Game) {
                        viewModel.resetGame()
                    }
                    if (currentDestination != screen.route) {
                        if (screen.route != null) {
                            navController.navigate(screen.route)
                        } else {
                            currentDialog = screen.dialogType
                        }
                    }
                }
            )
        }
    }

    when(currentDialog){
        DialogType.DIFFICULTY -> {
            DifficultyDialog(
                onSelectLevel = {
                    viewModel.updateDifficulty(it)
                },
                onDismiss = { currentDialog = null }
            )
        }
        DialogType.ABOUT -> {
            AboutDialog(
                onDismiss = { currentDialog = null }
            )
        }
        DialogType.EXIT -> {
            ExitDialog(
                onConfirmExit = {
                    if (context is Activity) context.finish()
                },
                onDismiss = { currentDialog = null }
            )
        }
        null-> {}
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavMenuPreview(){
    BottomNavMenu(
        navController = rememberNavController() , viewModel = TicTacToeViewModel(),
        context = LocalContext.current)
}
