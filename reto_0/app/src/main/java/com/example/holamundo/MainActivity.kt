package com.example.holamundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaMundoApp()
        }
    }
}

@Composable
fun HolaMundoApp() {

    var textColor by remember { mutableStateOf(Color.Black) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable (
                onClick = {
                    textColor = generateRandomColor()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hola Mundo",
            color = textColor,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


fun generateRandomColor(): Color {
    val red = (30..255).random()
    val green = (30..255).random()
    val blue = (30..255).random()
    return Color(red, green, blue)
}

@Preview(showBackground = true)
@Composable
fun PreviewHolaMundoApp() {
    HolaMundoApp()
}
