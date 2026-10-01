package com.example.calculadorademasa.iu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorademasa.viewmodel.BodyFitViewModel

@Composable
fun BodyFitScreen(
    viewModel: BodyFitViewModel,
    modifier: Modifier = Modifier
) {

    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        EncabezadoCalculadora()

        Spacer(modifier = Modifier.height(28.dp))

        CampoPeso(
            valor = estado.peso,
            alCambiar = {
                viewModel.cambiarPeso(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoAltura(
            valor = estado.altura,
            alCambiar = {
                viewModel.cambiarAltura(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoEdad(
            valor = estado.edad,
            alCambiar = {
                viewModel.cambiarEdad(it)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonCalcular(
            alCalcular = {
                viewModel.calcular()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (estado.error != null) {
            Text(
                text = estado.error!!,
                color = Color(0xFFD32F2F),
                fontWeight = FontWeight.Bold
            )

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
            color = Color(0xFF168AAD)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Calculadora de IMC",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Calcula tu índice de masa corporal",
            fontSize = 14.sp
        )
    }
}

@Composable
fun CampoPeso(
    valor: String,
    alCambiar: (String) -> Unit
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Peso")
        },
        placeholder = {
            Text("Ej. 60")
        },
        suffix = {
            Text("kg")
        },
        singleLine = true
    )
}

@Composable
fun CampoAltura(
    valor: String,
    alCambiar: (String) -> Unit
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Altura")
        },
        placeholder = {
            Text("Ej. 170")
        },
        suffix = {
            Text("cm")
        },
        singleLine = true
    )
}

@Composable
fun CampoEdad(
    valor: String,
    alCambiar: (String) -> Unit
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Edad")
        },
        placeholder = {
            Text("Ej. 20")
        },
        suffix = {
            Text("años")
        },
        singleLine = true
    )
}

@Composable
fun BotonCalcular(
    alCalcular: () -> Unit
) {

    Button(
        onClick = alCalcular,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "CALCULAR IMC",
            fontWeight = FontWeight.Bold
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
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5F6FA)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Tu resultado",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF168AAD)
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (imc != null) {

                val imcRedondeado =
                    ((imc * 10.0) + 0.5).toInt() / 10.0

                Text(
                    text = "IMC: $imcRedondeado",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF073B4C)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = categoria,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF168AAD)
            )

            Spacer(modifier = Modifier.height(18.dp))

            if (pesoMinimo != null && pesoMaximo != null) {

                val minimo =
                    ((pesoMinimo * 10.0) + 0.5).toInt() / 10.0

                val maximo =
                    ((pesoMaximo * 10.0) + 0.5).toInt() / 10.0

                Text(
                    text = "Rango de peso de referencia: $minimo kg - $maximo kg",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (aguaLitros != null) {

                val agua =
                    ((aguaLitros * 10.0) + 0.5).toInt() / 10.0

                Text(
                    text = "Agua aproximada: $agua litros al día",
                    fontSize = 14.sp
                )
            }

            if (vasosAgua != null) {

                Text(
                    text = "Aproximadamente $vasosAgua vasos de agua",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Recomendación",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF168AAD)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = recomendacion,
                fontSize = 14.sp
            )
        }
    }
}