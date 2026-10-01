package com.example.calculadorademasa.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculadorademasa.model.BodyFitUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BodyFitViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BodyFitUiState())

    val uiState: StateFlow<BodyFitUiState> = _uiState.asStateFlow()

    fun cambiarPeso(peso: String) {
        _uiState.value = _uiState.value.copy(
            peso = peso,
            error = null
        )
    }

    fun cambiarAltura(altura: String) {
        _uiState.value = _uiState.value.copy(
            altura = altura,
            error = null
        )
    }

    fun cambiarEdad(edad: String) {
        _uiState.value = _uiState.value.copy(
            edad = edad,
            error = null
        )
    }

    fun cambiarNivelActividad(nivel: String) {
        _uiState.value = _uiState.value.copy(
            nivelActividad = nivel
        )
    }

    fun calcular() {

        val peso: Double? = try {
            java.lang.Double.parseDouble(_uiState.value.peso)
        } catch (e: Exception) {
            null
        }

        val alturaCm: Double? = try {
            java.lang.Double.parseDouble(_uiState.value.altura)
        } catch (e: Exception) {
            null
        }

        val edad: Int? = try {
            java.lang.Integer.parseInt(_uiState.value.edad)
        } catch (e: Exception) {
            null
        }

        if (peso == null || peso <= 0.0) {
            mostrarError("Ingresa un peso válido.")
            return
        }

        if (alturaCm == null || alturaCm <= 0.0) {
            mostrarError("Ingresa una altura válida.")
            return
        }

        if (edad == null || edad < 18 || edad > 100) {
            mostrarError("La edad debe estar entre 18 y 100 años.")
            return
        }

        val altura = alturaCm / 100.0

        val imc: Double = peso / (altura * altura)

        val categoria = when {
            imc < 18.5 -> "Bajo peso"
            imc < 25.0 -> "Peso normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidad"
        }

        val pesoMinimo: Double = 18.5 * (altura * altura)
        val pesoMaximo: Double = 24.9 * (altura * altura)

        val factorActividad: Double = when (_uiState.value.nivelActividad) {
            "Sedentario" -> 0.030
            "Moderado" -> 0.033
            "Activo" -> 0.036
            else -> 0.033
        }

        val aguaLitros: Double = peso * factorActividad
        val vasosAgua: Int = ((aguaLitros / 0.25) + 0.5).toInt()

        val recomendacion = when {
            imc < 18.5 ->
                "Procura mantener una alimentación equilibrada y considera consultar a un profesional de la salud."

            imc < 25.0 ->
                "Tu resultado se encuentra dentro del rango de referencia. Mantén una alimentación equilibrada y actividad física regular."

            imc < 30.0 ->
                "Considera mantener una alimentación equilibrada, actividad física regular y hábitos saludables."

            else ->
                "Considera consultar a un profesional de la salud para recibir orientación personalizada."
        }

        _uiState.value = _uiState.value.copy(
            imc = imc,
            categoria = categoria,
            pesoMinimo = pesoMinimo,
            pesoMaximo = pesoMaximo,
            aguaLitros = aguaLitros,
            vasosAgua = vasosAgua,
            recomendacion = recomendacion,
            error = null
        )
    }

    private fun mostrarError(mensaje: String) {
        _uiState.value = _uiState.value.copy(
            error = mensaje
        )
    }

    fun limpiar() {
        _uiState.value = BodyFitUiState()
    }
}