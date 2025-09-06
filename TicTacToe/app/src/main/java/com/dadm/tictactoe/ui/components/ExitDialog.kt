package com.dadm.tictactoe.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExitDialog(
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirmExit
            ) {
                Text("Salir")
            }
        },
        title = {
            Text("¿Estás seguro que deseas salir?")
        },
        text = {
            Text("Si sales, perderás tu progreso actual.")
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ExitDialogPreview(){
    ExitDialog(onConfirmExit = {}, onDismiss = {})
}