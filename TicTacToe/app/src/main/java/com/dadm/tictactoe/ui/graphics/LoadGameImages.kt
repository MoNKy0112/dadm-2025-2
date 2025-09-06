package com.dadm.tictactoe.ui.graphics

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.dadm.tictactoe.R

@Composable
fun LoadGameImages(): Pair<ImageBitmap, ImageBitmap> {
    val xImage = ImageBitmap.imageResource(id = R.drawable.x_image)
    val oImage = ImageBitmap.imageResource(id = R.drawable.o_image)
    return xImage to oImage
}
