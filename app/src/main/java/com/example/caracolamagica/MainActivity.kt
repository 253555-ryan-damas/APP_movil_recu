package com.example.caracolamagica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CaracolaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PantallaPrincipal(viewModel)
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(viewModel: CaracolaViewModel) {

    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    var nombre by remember { mutableStateOf("") }
    var pregunta by remember { mutableStateOf("") }
    var desesperacion by remember { mutableStateOf(5f) }

    // Usamos Box para superponer los elementos encima de la imagen de fondo marino
    Box(modifier = Modifier.fillMaxSize()) {

        // Imagen de fondo (Asegúrate de tener un archivo llamado "fondomarino" en tu carpeta res/drawable)
        Image(
            painter = painterResource(id = R.drawable.fondomarino),
            contentDescription = "marino",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "La Caracola Mágica",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700) // Amarillo brillante para resaltar sobre el fondo marino
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Tu Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = pregunta,
                onValueChange = { pregunta = it },
                label = { Text("¿Qué deseas preguntar?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)) // Corregido de 1.dp a 12.dp para mantener el estilo
                    .background(Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.95f)) // Fondo blanco semitransparente para legibilidad
                    .padding(14.dp)
            ) {
                Text(
                    text = "A la izquierda preguntas no difíciles y a la derecha preguntas difíciles: ${desesperacion.toInt()} / 10",
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "(Osea altera tu respuesta sin embargo sigue siendo aleatorio por pura probabilidad)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = desesperacion,
                    onValueChange = { desesperacion = it },
                    valueRange = 1f..10f
                )
            }

            Button(
                onClick = { viewModel.consultarCaracola(nombre, pregunta, desesperacion) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00)) // Naranja llamativo
            ) {
                Text(
                    text = "Ver respuesta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            if (estado.mostrarResultado) {
                TarjetaResultado(
                    respuesta = estado.respuesta,
                    suerte = estado.porcentajeSuerte
                )
            }
        }
    }
}

@Composable
fun TarjetaResultado(respuesta: String, suerte: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Veredicto de la Caracola:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006064)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = respuesta, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Probabilidad de éxito: $suerte%", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = suerte / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF00ACC1)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PantallaPrincipal(viewModel = CaracolaViewModel())
        }
    }
}