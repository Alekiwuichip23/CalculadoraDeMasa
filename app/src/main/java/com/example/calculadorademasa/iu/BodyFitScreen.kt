package com.example.calculadorademasa.iu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorademasa.viewmodel.BodyFitViewModel

private val AzulBodyFit = Color(0xFF168AAD)
private val AzulBoton = Color(0xFF2196F3)
private val CelesteClaro = Color(0xFFE8F7FC)
private val TextoPrincipal = Color(0xFF111111)
private val TextoSecundario = Color(0xFF555555)
private val Fondo = Color(0xFFF8FCFE)
private val Blanco = Color.White
private val RojoError = Color(0xFFD32F2F)

@Composable
fun BodyFitScreen(
    viewModel: BodyFitViewModel,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Fondo)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EncabezadoCalculadora()

        Spacer(modifier = Modifier.height(28.dp))

        CampoPeso(
            valor = estado.peso,
            alCambiar = { viewModel.cambiarPeso(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoAltura(
            valor = estado.altura,
            alCambiar = { viewModel.cambiarAltura(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoEdad(
            valor = estado.edad,
            alCambiar = { viewModel.cambiarEdad(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonCalcular(
            alCalcular = { viewModel.calcular() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (estado.error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = estado.error!!,
                    modifier = Modifier.padding(16.dp),
                    color = RojoError,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (estado.imc != null) {
            ResultadoIMC(
                imc = estado.imc,
                categoria = estado.categoria,
                pesoMinimo = estado.pesoMinimo,
                pesoMaximo = estado.pesoMaximo,
                aguaLitros = estado.aguaLitros,
                vasosAgua = estado.vasosAgua,
                recomendacion = estado.recomendacion
            )
        }
    }
}

@Composable
fun EncabezadoCalculadora() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BODYFIT",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AzulBodyFit
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Calculadora de IMC",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Calcula tu índice de masa corporal",
            fontSize = 14.sp,
            color = TextoSecundario
        )
    }
}

@Composable
fun CampoPeso(
    valor: String,
    alCambiar: (String) -> Unit
) {
    CampoEntrada(
        valor = valor,
        alCambiar = alCambiar,
        etiqueta = "Peso",
        ejemplo = "Ej. 60",
        unidad = "kg"
    )
}

@Composable
fun CampoAltura(
    valor: String,
    alCambiar: (String) -> Unit
) {
    CampoEntrada(
        valor = valor,
        alCambiar = alCambiar,
        etiqueta = "Altura",
        ejemplo = "Ej. 170",
        unidad = "cm"
    )
}

@Composable
fun CampoEdad(
    valor: String,
    alCambiar: (String) -> Unit
) {
    CampoEntrada(
        valor = valor,
        alCambiar = alCambiar,
        etiqueta = "Edad",
        ejemplo = "Ej. 20",
        unidad = "años"
    )
}

@Composable
fun CampoEntrada(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    ejemplo: String,
    unidad: String
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = etiqueta,
                color = TextoSecundario
            )
        },
        placeholder = {
            Text(
                text = ejemplo,
                color = Color(0xFF999999)
            )
        },
        suffix = {
            Text(
                text = unidad,
                color = TextoSecundario
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextoPrincipal,
            unfocusedTextColor = TextoPrincipal,
            focusedContainerColor = Blanco,
            unfocusedContainerColor = Blanco,
            focusedBorderColor = AzulBodyFit,
            unfocusedBorderColor = AzulBodyFit,
            focusedLabelColor = AzulBodyFit,
            unfocusedLabelColor = TextoSecundario,
            cursorColor = AzulBodyFit
        )
    )
}

@Composable
fun BotonCalcular(
    alCalcular: () -> Unit
) {
    Button(
        onClick = alCalcular,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AzulBoton,
            contentColor = Blanco
        )
    ) {
        Text(
            text = "CALCULAR IMC",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ResultadoIMC(
    imc: Double?,
    categoria: String,
    pesoMinimo: Double?,
    pesoMaximo: Double?,
    aguaLitros: Double?,
    vasosAgua: Int?,
    recomendacion: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CelesteClaro
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Tu resultado",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "IMC",
                fontSize = 14.sp,
                color = TextoSecundario
            )

            Text(
                text = java.lang.String.format("%.1f", imc ?: 0.0),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = categoria,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(20.dp))

            FilaResultado(
                titulo = "Peso de referencia",
                valor = if (pesoMinimo != null && pesoMaximo != null) {
                    "${java.lang.String.format("%.1f", pesoMinimo)} kg - ${java.lang.String.format("%.1f", pesoMaximo)} kg"
                } else {
                    "--"
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilaResultado(
                titulo = "Agua aproximada",
                valor = if (aguaLitros != null) {
                    "${java.lang.String.format("%.1f", aguaLitros)} litros al día"
                } else {
                    "--"
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilaResultado(
                titulo = "Consumo aproximado",
                valor = if (vasosAgua != null) {
                    "Aproximadamente $vasosAgua vasos de agua"
                } else {
                    "--"
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Recomendación",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = recomendacion,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextoPrincipal
            )
        }
    }
}

@Composable
fun FilaResultado(
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = titulo,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = valor,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextoPrincipal,
            textAlign = TextAlign.End
        )
    }
}