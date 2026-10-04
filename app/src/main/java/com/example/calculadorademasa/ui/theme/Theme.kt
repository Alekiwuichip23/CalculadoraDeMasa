package com.example.calculadorademasa.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BodyFitColorScheme = lightColorScheme(
    primary = AzulBodyFit,
    onPrimary = Blanco,
    primaryContainer = CelesteClaro,
    onPrimaryContainer = AzulOscuro,

    secondary = AzulBoton,
    onSecondary = Blanco,
    secondaryContainer = AzulMuyClaro,
    onSecondaryContainer = AzulOscuro,

    tertiary = AzulBodyFit,
    onTertiary = Blanco,

    background = FondoBodyFit,
    onBackground = TextoPrincipal,

    surface = Blanco,
    onSurface = TextoPrincipal,

    surfaceVariant = CelesteClaro,
    onSurfaceVariant = TextoSecundario,

    outline = AzulBodyFit,
    outlineVariant = AzulClaro,

    error = RojoError,
    onError = Blanco
)

@Composable
fun CalculadoraDeMasaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BodyFitColorScheme,
        typography = Typography,
        content = content
    )
}