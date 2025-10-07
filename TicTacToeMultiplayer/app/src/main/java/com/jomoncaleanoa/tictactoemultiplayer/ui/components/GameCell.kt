package com.jomoncaleanoa.tictactoemultiplayer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.tictactoemultiplayer.model.Player
import com.jomoncaleanoa.tictactoemultiplayer.ui.graphics.TicTacToeColorPalette

@Composable
fun GameCell(player: Player, onClick:()->Unit) {
    Box(modifier = Modifier
        .size(100.dp)
        .padding(4.dp)
        .aspectRatio(1f)
        .background(
            color = when(player){
                Player.X -> TicTacToeColorPalette.XPlayerColor
                Player.O -> TicTacToeColorPalette.OPlayerColor
                Player.NONE -> TicTacToeColorPalette.NonePlayerColor
            }
        )
        .clickable { onClick() }
    ){
        Text(
            text = when(player){
                Player.X -> "X"
                Player.O -> "O"
                Player.NONE -> ""
            },
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameCellXPreview() {
    GameCell(Player.X){}
}

@Preview(showBackground = true)
@Composable
fun GameCellOPreview() {
    GameCell(Player.O){}
}

@Preview(showBackground = true)
@Composable
fun GameCellNonePreview() {
    GameCell(Player.NONE){}
}