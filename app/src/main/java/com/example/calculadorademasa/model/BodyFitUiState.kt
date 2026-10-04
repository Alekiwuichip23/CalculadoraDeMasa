package com.example.calculadorademasa.model

data class BodyFitUiState(
    val peso: String = "",
    val altura: String = "",
    val edad: String = "",
    val sexo: String = "Hombre",
    val nivelActividad: String = "Moderado",
    val vasosConsumidos: Int = 6,
    val vasosConsumidosEntrada: String = "6",
    val pantallaActual: String = "calcular",

    val imc: Double? = null,
    val categoria: String = "",
    val interpretacionImc: String = "",
    val pesoMinimo: Double? = null,
    val pesoMaximo: Double? = null,
    val aguaLitros: Double? = null,
    val vasosAgua: Int? = null,
    val vasosFaltantes: Int = 0,
    val progresoHidratacion: Float = 0f,

    val imcTexto: String = "--",
    val rangoPesoTexto: String = "--",
    val aguaTexto: String = "--",
    val vasosAguaTexto: String = "--",
    val edadPerfilTexto: String = "--",
    val pesoPerfilTexto: String = "--",
    val alturaPerfilTexto: String = "--",
    val consumoHabitualTexto: String = "Tu consumo habitual: 6 vasos al día",
    val estimacionVasosTexto: String = "",
    val mensajeHidratacion: String = "Realiza el cálculo para obtener una estimación.",
    val recomendacion: String = "Realiza tu cálculo para recibir recomendaciones generales.",
    val error: String? = null
)