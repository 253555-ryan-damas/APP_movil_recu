package com.example.caracolamagica

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaracolaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CaracolaState())
    val uiState: StateFlow<CaracolaState> = _uiState.asStateFlow()

    private val respuestasPosibles = listOf("Sí", "Si tu quieres hazlo", "Prueba preguntando de nuevo con con mas nivel","la respuesta son los amigos que hicimos en el camino")
    private val respuestasNegativas = listOf("No", "Si vas a preguntar eso no preguntes", "Pregúntale a tu compa mjr","hay veces donde tampoco se que responder")

    fun consultarCaracola(nombre: String, pregunta: String, nivelDesesperacion: Float) {
        if (nombre.isNotBlank() && pregunta.isNotBlank()) {

            val respuestaElegida = if (nivelDesesperacion > 7f) {
                respuestasNegativas.random()
            } else {
                respuestasPosibles.random()
            }

            val calculoSuerte = ((11f - nivelDesesperacion) * 10).toInt()

            _uiState.value = CaracolaState(
                respuesta = "$nombre, la caracola dice: $respuestaElegida",
                porcentajeSuerte = calculoSuerte,
                mostrarResultado = true
            )
        }
    }
}