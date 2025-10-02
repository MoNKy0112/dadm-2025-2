package com.dadm.tictactoe.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import com.dadm.tictactoe.ui.graphics.TicTacToeColorPalette

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.dadm.tictactoe.Model.Player
import com.dadm.tictactoe.R
import com.dadm.tictactoe.ViewModel.TicTacToeViewModel

@Composable
fun GameBoard(
    viewModel: TicTacToeViewModel,
    xImage: ImageBitmap,
    oImage: ImageBitmap,
    modifier: Modifier
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val boardSize = minOf(this.maxWidth, this.maxHeight) // cuadrado adaptable
        var cellSize by remember { mutableStateOf(0f) }

        Canvas(
            modifier = Modifier
                .size(boardSize) // ahora nunca se pasa del alto
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val col = (offset.x / cellSize).toInt()
                        val row = (offset.y / cellSize).toInt()
                        viewModel.makeMove(row, col)
                    }
                }
                .aspectRatio(1f)
        ) {
            cellSize = size.width / 3
            val imageSize = cellSize * 0.8f

            // Líneas
            for (i in 1..2) {
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

            // Dibujar imágenes
            viewModel.game.board.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, player ->
                    val x = colIndex * cellSize
                    val y = rowIndex * cellSize
                    val image = when (player) {
                        Player.X -> xImage
                        Player.O -> oImage
                        else -> null
                    }
                    image?.let {
                        drawImage(
                            image = it,
                            dstSize = IntSize(imageSize.toInt(), imageSize.toInt()),
                            dstOffset = IntOffset(
                                x.toInt() + (cellSize.toInt() - imageSize.toInt()) / 2,
                                y.toInt() + (cellSize.toInt() - imageSize.toInt()) / 2
                            )
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    GameBoard(
        TicTacToeViewModel(
            soundUseCase = null,
            prefs = null,
            state = null
        ),
        xImage = ImageBitmap.imageResource(id = R.drawable.x_image),
        oImage = ImageBitmap.imageResource(id = R.drawable.o_image),
        modifier = Modifier
            .size(300.dp)
            .padding(16.dp)
    )
}
