package com.dadm.tictactoe.ui.components

import androidx.compose.foundation.Canvas
import com.dadm.tictactoe.ui.graphics.TicTacToeColorPalette

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.dadm.tictactoe.Model.Player
import com.dadm.tictactoe.R
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun GameBoard(
    viewModel: TicTacToeViewModel,
    xImage: ImageBitmap,
    oImage: ImageBitmap
) {

    var cellSize by remember { mutableStateOf(0f) }

    Canvas(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures{
                    offset ->
                    val col = (offset.x / cellSize).toInt()
                    val row = (offset.y / cellSize).toInt()
                    viewModel.makeMove(row, col)

                }
            }
            .fillMaxWidth()
            .aspectRatio(1f)
    ){
        cellSize = size.width / 3
        val imageSize = cellSize * 0.8f
        for (i in 1..2){
            drawLine(
                color = TicTacToeColorPalette.BorderColor,
                start = Offset(cellSize * i, 0f),
                end = Offset(cellSize * i, size.height),
                strokeWidth = 4f
            )
            drawLine(
                color = TicTacToeColorPalette.BorderColor,
                start = Offset(0f, cellSize * i),
                end = Offset(size.width, cellSize * i),
                strokeWidth = 4f
            )
        }

        //Dibujar las imagenes
        viewModel.game.board.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, player ->
                val x = colIndex * cellSize
                val y = rowIndex * cellSize
                val image = when(player){
                    Player.X -> xImage
                    Player.O -> oImage
                    else -> null
                }
                image?.let {
                    drawImage(
                        image = it,
                        dstSize = IntSize(imageSize.toInt(), imageSize.toInt()),
                        dstOffset = IntOffset(x.toInt() + (cellSize.toInt() - imageSize.toInt()) / 2, y.toInt()+ (cellSize.toInt() - imageSize.toInt()) / 2)
                    )
                }
            }
        }
    }

//        viewModel.game.board.forEachIndexed { rowIndex, row ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                row.forEachIndexed { colIndex, player ->
//                    GameCell(player, onClick = {
//                        viewModel.makeMove(rowIndex, colIndex)
//                    })
//                }
//            }
//        }

        /*Button(onClick = { viewModel.resetGame() }) {
            Text("Reiniciar")
        }*/

}

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    GameBoard(
        TicTacToeViewModel(),
        xImage = ImageBitmap.imageResource(id = R.drawable.x_image),
        oImage = ImageBitmap.imageResource(id = R.drawable.o_image),
    )
}
