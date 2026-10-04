package com.example.calculadorademasa.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculadorademasa.model.BodyFitUiState
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BodyFitViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BodyFitUiState())
    val uiState: StateFlow<BodyFitUiState> = _uiState.asStateFlow()

    fun cambiarPeso(peso: String) {
        actualizarEntrada(_uiState.value.copy(peso = peso))
    }

    fun cambiarAltura(altura: String) {
        actualizarEntrada(_uiState.value.copy(altura = altura))
    }

    fun cambiarEdad(edad: String) {
        actualizarEntrada(_uiState.value.copy(edad = edad))
    }

    fun cambiarSexo(sexo: String) {
        if (sexo == "Hombre" || sexo == "Mujer") {
            _uiState.value = _uiState.value.copy(sexo = sexo)
        }
    }

    fun cambiarNivelActividad(nivel: String) {
        if (nivel == "Sedentario" || nivel == "Moderado" || nivel == "Activo") {
            actualizarEntrada(_uiState.value.copy(nivelActividad = nivel))
        }
    }

    fun cambiarVasosConsumidos(vasos: String) {
        // Conserva la diferencia entre un campo vacío y escribir "0".
        val cantidad = if (vasos == "") 0 else convertirEntero(vasos)

        if (cantidad != null && cantidad >= 0 && cantidad <= 30) {
            actualizarEntrada(
                _uiState.value.copy(
                    vasosConsumidos = cantidad,
                    vasosConsumidosEntrada = vasos
                )
            )
        } else {
            _uiState.value = _uiState.value.copy(
                error = "Ingresa una cantidad de vasos entre 0 y 30."
            )
        }
    }

    fun cambiarPantalla(pantalla: String) {
        if (pantalla == "calcular" || pantalla == "perfil") {
            _uiState.value = _uiState.value.copy(pantallaActual = pantalla)
        }
    }

    fun calcular() {
        _uiState.value = prepararEstado(_uiState.value, mostrarErrores = true)
    }

    private fun actualizarEntrada(nuevoEstado: BodyFitUiState) {
        // Recalcula al escribir; no muestra errores mientras la entrada está incompleta.
        _uiState.value = prepararEstado(nuevoEstado, mostrarErrores = false)
    }

    private fun prepararEstado(
        entrada: BodyFitUiState,
        mostrarErrores: Boolean
    ): BodyFitUiState {
        // Limpia resultados anteriores antes de validar los nuevos datos.
        val estado = entrada.copy(
            imc = null,
            categoria = "",
            interpretacionImc = "",
            pesoMinimo = null,
            pesoMaximo = null,
            aguaLitros = null,
            vasosAgua = null,
            vasosFaltantes = 0,
            progresoHidratacion = 0f,
            imcTexto = "--",
            rangoPesoTexto = "--",
            aguaTexto = "--",
            vasosAguaTexto = "--",
            edadPerfilTexto = textoConUnidad(entrada.edad, "años"),
            pesoPerfilTexto = textoConUnidad(entrada.peso, "kg"),
            alturaPerfilTexto = textoConUnidad(entrada.altura, "cm"),
            consumoHabitualTexto = "Tu consumo habitual: ${entrada.vasosConsumidos} vasos al día",
            estimacionVasosTexto = "",
            mensajeHidratacion = "Realiza el cálculo para obtener una estimación.",
            recomendacion = "Realiza tu cálculo para recibir recomendaciones generales.",
            error = null
        )

        val peso = convertirDecimal(estado.peso)
        val alturaCm = convertirDecimal(estado.altura)
        val edad = convertirEntero(estado.edad)

        val mensajeError = when {
            peso == null || peso <= 0.0 -> "Ingresa un peso válido."
            alturaCm == null || alturaCm <= 0.0 -> "Ingresa una altura válida en centímetros."
            edad == null || edad < 18 || edad > 100 -> "La edad debe estar entre 18 y 100 años."
            else -> null
        }

        if (mensajeError != null) {
            return estado.copy(error = if (mostrarErrores) mensajeError else null)
        }

        // Estas comprobaciones permiten usar valores no nulos en las fórmulas.
        if (peso == null || alturaCm == null || edad == null) return estado

        val alturaMetros = alturaCm / 100.0
        val alturaCuadrada = alturaMetros * alturaMetros
        val imc = peso / alturaCuadrada
        val pesoMinimo = 18.5 * alturaCuadrada
        val pesoMaximo = 24.9 * alturaCuadrada

        val factorActividad = when (estado.nivelActividad) {
            "Sedentario" -> 0.030
            "Activo" -> 0.036
            else -> 0.033
        }
        val aguaLitros = peso * factorActividad
        val vasosCalculados = (aguaLitros / 0.25) + 0.5

        // Evita NaN, infinito y cantidades fuera de la representación de Int.
        if (!java.lang.Double.isFinite(imc) || imc <= 0.0 ||
            !java.lang.Double.isFinite(pesoMinimo) ||
            !java.lang.Double.isFinite(pesoMaximo) ||
            !java.lang.Double.isFinite(vasosCalculados) ||
            vasosCalculados > Int.MAX_VALUE
        ) {
            return estado.copy(
                error = if (mostrarErrores) "Revisa el peso y la altura introducidos." else null
            )
        }

        val categoria: String
        val interpretacion: String
        val recomendacion: String

        // Una sola selección determina los tres textos de cada categoría.
        when {
            imc < 18.5 -> {
                categoria = "Bajo peso"
                interpretacion = "Tu resultado se encuentra por debajo del rango de referencia."
                recomendacion = "Procura mantener una alimentación equilibrada y considera consultar a un profesional de la salud."
            }
            imc < 25.0 -> {
                categoria = "Peso normal"
                interpretacion = "Tu resultado se encuentra dentro del rango de referencia."
                recomendacion = "Tu resultado se encuentra dentro del rango de referencia. Mantén una alimentación equilibrada y actividad física regular."
            }
            imc < 30.0 -> {
                categoria = "Sobrepeso"
                interpretacion = "Tu resultado se encuentra por encima del rango de referencia."
                recomendacion = "Considera mantener una alimentación equilibrada, actividad física regular y hábitos saludables."
            }
            else -> {
                categoria = "Obesidad"
                interpretacion = "Tu resultado se encuentra en un rango elevado."
                recomendacion = "Considera consultar a un profesional de la salud para recibir orientación personalizada."
            }
        }

        val vasosAgua = vasosCalculados.toInt()
        val diferencia = vasosAgua - estado.vasosConsumidos
        val vasosFaltantes = if (diferencia > 0) diferencia else 0
        val proporcion = if (vasosAgua > 0) {
            estado.vasosConsumidos.toFloat() / vasosAgua.toFloat()
        } else {
            0f
        }
        val progreso = when {
            proporcion < 0f -> 0f
            proporcion > 1f -> 1f
            else -> proporcion
        }
        val mensajeHidratacion = if (vasosFaltantes > 0) {
            "Te faltan aproximadamente $vasosFaltantes vasos para alcanzar la estimación."
        } else {
            "Has alcanzado o superado la estimación diaria."
        }

        return estado.copy(
            imc = imc,
            categoria = categoria,
            interpretacionImc = interpretacion,
            pesoMinimo = pesoMinimo,
            pesoMaximo = pesoMaximo,
            aguaLitros = aguaLitros,
            vasosAgua = vasosAgua,
            vasosFaltantes = vasosFaltantes,
            progresoHidratacion = progreso,
            imcTexto = formatearNumero(imc),
            rangoPesoTexto = "${formatearNumero(pesoMinimo)} kg - ${formatearNumero(pesoMaximo)} kg",
            aguaTexto = "${formatearNumero(aguaLitros)} litros al día",
            vasosAguaTexto = "Aproximadamente $vasosAgua vasos de agua",
            estimacionVasosTexto = "Estimación BodyFit: $vasosAgua vasos al día",
            mensajeHidratacion = mensajeHidratacion,
            recomendacion = recomendacion
        )
    }

    private fun convertirDecimal(texto: String): Double? {
        return try {
            val valor = java.lang.Double.parseDouble(texto)
            if (java.lang.Double.isFinite(valor)) valor else null
        } catch (_: NumberFormatException) {
            null
        }
    }

    private fun convertirEntero(texto: String): Int? {
        return try {
            java.lang.Integer.parseInt(texto)
        } catch (_: NumberFormatException) {
            null
        }
    }

    private fun formatearNumero(valor: Double): String {
        return java.lang.String.format(
            Locale.US,
            "%.1f",
            valor
        )
    }

    private fun textoConUnidad(texto: String, unidad: String): String {
        return if (texto == "") "--" else "$texto $unidad"
    }
}
