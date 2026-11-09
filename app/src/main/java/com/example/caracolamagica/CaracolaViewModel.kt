package com.example.caracolamagica

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaracolaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CaracolaState())
    val uiState: StateFlow<CaracolaState> = _uiState.asStateFlow()

    private val respuestasPosibles = listOf("Sí","No","Si Dios quiere y si eres catol,si la naturaleza quiere", "Prueba preguntando de nuevo","yo digo que lo mates")

    private val respuestasNegativas = listOf("No","Nose","si","Si vas a preguntar eso no preguntes", "Pregúntale al de al lado")

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