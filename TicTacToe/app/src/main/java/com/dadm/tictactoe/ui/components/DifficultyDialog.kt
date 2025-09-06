package com.dadm.tictactoe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dadm.tictactoe.ViewModel.Difficulty

@Composable
fun DifficultyDialog(
    onDismiss: () -> Unit,
    onSelectLevel: (Difficulty) -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
                Text(text ="Selecciona la dificultad:")
        },
        text = {
               Column {
                   Button(onClick = { onSelectLevel(Difficulty.EASY); onDismiss() }) {
                       Text("Facil")
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   Button(onClick = { onSelectLevel(Difficulty.MEDIUM); onDismiss() }) {
                       Text("Medio")
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   Button(onClick = { onSelectLevel(Difficulty.IMPOSSIBLE); onDismiss() }) {
                       Text("Dificil")
                   }
               }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DifficultyDialogPreview(){
    DifficultyDialog(onDismiss = {}, onSelectLevel = {})
}