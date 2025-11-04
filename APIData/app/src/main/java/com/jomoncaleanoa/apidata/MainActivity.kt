package com.jomoncaleanoa.apidata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jomoncaleanoa.apidata.ui.MapViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MapViewModel()
        setContent {
            MaterialTheme {
                MapScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel) {
    var mapId by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var mapInfo by remember { mutableStateOf(viewModel.currentMap) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mapas de Colombia") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = mapId,
                onValueChange = { mapId = it },
                label = { Text("ID del mapa (ej: 1, 2, 3...)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (mapId.isNotBlank()) {
                        loading = true
                        viewModel.fetchMapById(mapId.toInt()) {
                            mapInfo = viewModel.currentMap
                            error = viewModel.errorMessage
                            loading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Consultar")
            }

            Spacer(Modifier.height(24.dp))

            when {
                loading -> CircularProgressIndicator()
                error != null -> Text(error!!, color = MaterialTheme.colorScheme.error)
                mapInfo != null -> MapDetails(mapInfo!!)
            }
        }
    }
}

@Composable
fun MapDetails(map: com.jomoncaleanoa.apidata.data.MapInfo) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(map.name, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(map.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
        if (map.urlImages.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(map.urlImages[0]),
                contentDescription = map.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Fuente: ${map.urlSource}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
