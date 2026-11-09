package com.example.caracolamagica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CaracolaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaPrincipal(viewModel)
            }
        }
    }
}

@Composable
fun PantallaPrincipal(viewModel: CaracolaViewModel) {

    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    var nombre by remember { mutableStateOf("") }
    var pregunta by remember { mutableStateOf("") }
    var energia by remember { mutableStateOf(5f) }

    Column(
        modifier = Modifier.padding(20.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Caracola Mágica", style = MaterialTheme.typography.headlineLarge)

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Tu Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pregunta,
            onValueChange = { pregunta = it },
            label = { Text("Tu Pregunta") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Nivel de desesperacion: ${energia.toInt()}")
        Slider(
            value = energia,
            onValueChange = { energia = it },
            valueRange = 1f..10f
        )


        Button(
            onClick = { viewModel.consultarCaracola(nombre, pregunta, energia) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Respuesta")
        }


        if (estado.mostrarResultado) {
            TarjetaResultado(
                respuesta = estado.respuesta,
                suerte = estado.porcentajeSuerte
            )
        }
    }
}

@Composable
fun TarjetaResultado(respuesta: String, suerte: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Resultado:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = respuesta, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Probabilidad de éxito: $suerte%")
            LinearProgressIndicator(
                progress = suerte / 100f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    MaterialTheme {
        PantallaPrincipal(viewModel = CaracolaViewModel())
    }
}

