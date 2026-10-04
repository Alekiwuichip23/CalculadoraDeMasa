package com.example.calculadorademasa.model

data class BodyFitUiState(
    val peso: String = "",
    val altura: String = "",
    val edad: String = "",
    val sexo: String = "Hombre",
    val nivelActividad: String = "Moderado",
    val vasosConsumidos: Int = 6,
    val pantallaActual: String = "calcular",

    val imc: Double? = null,
    val categoria: String = "",
    val pesoMinimo: Double? = null,
    val pesoMaximo: Double? = null,

    val aguaLitros: Double? = null,
    val vasosAgua: Int? = null,

    val recomendacion: String = "",
    val error: String? = null
)