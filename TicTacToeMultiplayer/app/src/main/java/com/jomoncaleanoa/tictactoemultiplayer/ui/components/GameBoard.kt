package com.jomoncaleanoa.tictactoemultiplayer.ui.components

import androidx.compose.foundation.Canvas
import com.jomoncaleanoa.tictactoemultiplayer.ui.graphics.TicTacToeColorPalette

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.tictactoemultiplayer.model.Player

import com.jomoncaleanoa.tictactoemultiplayer.ui.graphics.LoadGameImages
import kotlin.collections.forEachIndexed
import kotlin.let

@Composable
fun GameBoard(
    board: List<List<Player>>,
    onCellClick: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val (xImage, oImage) = LoadGameImages()
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val boardSize =
            kotlin.comparisons.minOf(this.maxWidth, this.maxHeight) // cuadrado adaptable
        var cellSize by remember { mutableStateOf(0f) }

        Canvas(
            modifier = Modifier
                .size(boardSize) // ahora nunca se pasa del alto
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val col = (offset.x / cellSize).toInt()
                        val row = (offset.y / cellSize).toInt()
                        onCellClick(row, col)
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
            board.forEachIndexed { r, row ->
                row.forEachIndexed { c, p ->
                    val x = c * cellSize
                    val y = r * cellSize
                    when (p) {
                        Player.X -> drawImage(
                            image = xImage,
                            dstSize = IntSize(imageSize.toInt(), imageSize.toInt()),
                            dstOffset = IntOffset(
                                (x + (cellSize - imageSize) / 2f).toInt(),
                                (y + (cellSize - imageSize) / 2f).toInt()
                            )
                        )
                        Player.O -> drawImage(
                            image = oImage,
                            dstSize = IntSize(imageSize.toInt(), imageSize.toInt()),
                            dstOffset = IntOffset(
                                (x + (cellSize - imageSize) / 2f).toInt(),
                                (y + (cellSize - imageSize) / 2f).toInt()
                            )
                        )
                        else -> {}
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
        board = listOf(
            listOf(Player.X, Player.O, Player.X),
            listOf(Player.O, Player.X, Player.NONE),
            listOf(Player.NONE, Player.O, Player.NONE)
        ),
        modifier = Modifier
            .size(300.dp)
            .padding(16.dp),
        onCellClick = { _, _ -> }
    )
}
