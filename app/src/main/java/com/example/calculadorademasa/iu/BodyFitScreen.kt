package com.example.calculadorademasa.iu

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculadorademasa.ui.theme.AzulClaro
import com.example.calculadorademasa.viewmodel.BodyFitViewModel

private val AzulBodyFit = Color(0xFF168AAD)
private val AzulBoton = Color(0xFF2196F3)
private val AzulOscuro = Color(0xFF075985)
private val CelesteClaro = Color(0xFFE8F7FC)
private val AzulMuyClaro = Color(0xFFE3F2FD)
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
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Fondo)
            .safeDrawingPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (estado.pantallaActual == "calcular") {
                PantallaCalcular(
                    estado = estado,
                    viewModel = viewModel
                )
            } else {
                PantallaPerfil(
                    estado = estado,
                    viewModel = viewModel
                )
            }
        }

        BarraNavegacion(
            pantallaActual = estado.pantallaActual,
            alSeleccionar = {
                viewModel.cambiarPantalla(it)
            }
        )
    }
}

@Composable
fun PantallaCalcular(
    estado: com.example.calculadorademasa.model.BodyFitUiState,
    viewModel: BodyFitViewModel
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

    Spacer(modifier = Modifier.height(20.dp))

    SelectorSexo(
        sexoSeleccionado = estado.sexo,
        alSeleccionar = {
            viewModel.cambiarSexo(it)
        }
    )

    Spacer(modifier = Modifier.height(20.dp))

    SelectorActividad(
        actividadSeleccionada = estado.nivelActividad,
        alSeleccionar = {
            viewModel.cambiarNivelActividad(it)
        }
    )

    Spacer(modifier = Modifier.height(20.dp))

    CampoAgua(
        vasos = estado.vasosConsumidos,
        alCambiar = {
            viewModel.cambiarVasosConsumidos(it)
        }
    )

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = {
            viewModel.calcular()
        },
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
                text = estado.error,
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
            interpretacionImc = estado.interpretacionImc,
            progresoImc = estado.progresoImc,
            pesoMinimo = estado.pesoMinimo,
            pesoMaximo = estado.pesoMaximo,
            aguaLitros = estado.aguaLitros,
            vasosAgua = estado.vasosAgua,
            vasosConsumidos = estado.vasosConsumidos,
            vasosFaltantes = estado.vasosFaltantes,
            progresoHidratacion = estado.progresoHidratacion,
            recomendacion = estado.recomendacion
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
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
            text = "Conoce mejor tus hábitos de salud",
            fontSize = 14.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center
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
            Text(etiqueta)
        },
        placeholder = {
            Text(ejemplo)
        },
        suffix = {
            Text(unidad)
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
fun SelectorSexo(
    sexoSeleccionado: String,
    alSeleccionar: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Sexo",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OpcionSeleccion(
                texto = "Hombre",
                seleccionado = sexoSeleccionado == "Hombre",
                alSeleccionar = {
                    alSeleccionar("Hombre")
                },
                modifier = Modifier.weight(1f)
            )

            OpcionSeleccion(
                texto = "Mujer",
                seleccionado = sexoSeleccionado == "Mujer",
                alSeleccionar = {
                    alSeleccionar("Mujer")
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SelectorActividad(
    actividadSeleccionada: String,
    alSeleccionar: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Nivel de actividad",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OpcionSeleccion(
                texto = "Sedentario",
                seleccionado = actividadSeleccionada == "Sedentario",
                alSeleccionar = {
                    alSeleccionar("Sedentario")
                },
                modifier = Modifier.weight(1f)
            )

            OpcionSeleccion(
                texto = "Moderado",
                seleccionado = actividadSeleccionada == "Moderado",
                alSeleccionar = {
                    alSeleccionar("Moderado")
                },
                modifier = Modifier.weight(1f)
            )

            OpcionSeleccion(
                texto = "Activo",
                seleccionado = actividadSeleccionada == "Activo",
                alSeleccionar = {
                    alSeleccionar("Activo")
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun OpcionSeleccion(
    texto: String,
    seleccionado: Boolean,
    alSeleccionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = alSeleccionar,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (seleccionado) AzulBodyFit else Blanco,
            contentColor = if (seleccionado) Blanco else AzulBodyFit
        ),
        border = if (!seleccionado) {
            BorderStroke(
                width = 1.dp,
                color = AzulBodyFit
            )
        } else {
            null
        }
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            fontWeight = if (seleccionado) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CampoAgua(
    vasos: Int,
    alCambiar: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Consumo de agua",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "¿Cuántos vasos de agua tomas normalmente al día?",
            fontSize = 13.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = if (vasos == 0) "" else vasos.toString(),
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Vasos al día")
            },
            suffix = {
                Text("vasos")
            },
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
}

@Composable
fun ResultadoIMC(
    imc: Double,
    categoria: String,
    interpretacionImc: String,
    progresoImc: Float,
    pesoMinimo: Double?,
    pesoMaximo: Double?,
    aguaLitros: Double?,
    vasosAgua: Int?,
    vasosConsumidos: Int,
    vasosFaltantes: Int,
    progresoHidratacion: Float,
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "IMC",
                fontSize = 14.sp,
                color = TextoSecundario
            )

            Text(
                text = java.lang.String.format("%.1f", imc),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Text(
                text = categoria,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = interpretacionImc,
                fontSize = 13.sp,
                color = TextoSecundario,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Indicador de IMC",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = {
                    progresoImc
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = AzulBodyFit,
                trackColor = Blanco
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bajo",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "Normal",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "Sobrepeso",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "Obesidad",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )
            }

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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Hidratación",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$vasosConsumidos de ${vasosAgua ?: 0} vasos",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = {
                    progresoHidratacion
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = AzulBodyFit,
                trackColor = Blanco
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (vasosFaltantes > 0) {
                Text(
                    text = "Te faltan aproximadamente $vasosFaltantes vasos para alcanzar la estimación.",
                    fontSize = 13.sp,
                    color = TextoSecundario,
                    lineHeight = 18.sp
                )
            } else {
                Text(
                    text = "Has alcanzado la estimación diaria de hidratación.",
                    fontSize = 13.sp,
                    color = AzulBodyFit,
                    fontWeight = FontWeight.Medium
                )
            }

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

@Composable
fun PantallaPerfil(
    estado: com.example.calculadorademasa.model.BodyFitUiState,
    viewModel: BodyFitViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Mi perfil",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Resumen de tus datos y resultados",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        TarjetaPerfil(
            estado = estado
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaEstado(
            estado = estado
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaHidratacion(
            estado = estado
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaRecomendaciones(
            recomendacion = estado.recomendacion,
            estado = estado
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.cambiarPantalla("calcular")
            },
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
                text = "EDITAR MIS DATOS",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TarjetaPerfil(
    estado: com.example.calculadorademasa.model.BodyFitUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blanco
        ),
        border = BorderStroke(
            width = 1.dp,
            color = AzulBodyFit
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Información personal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilaPerfil(
                titulo = "Edad",
                valor = if (estado.edad == "") "--" else "${estado.edad} años"
            )

            FilaPerfil(
                titulo = "Sexo",
                valor = estado.sexo
            )

            FilaPerfil(
                titulo = "Peso",
                valor = if (estado.peso == "") "--" else "${estado.peso} kg"
            )

            FilaPerfil(
                titulo = "Altura",
                valor = if (estado.altura == "") "--" else "${estado.altura} cm"
            )

            FilaPerfil(
                titulo = "Actividad",
                valor = estado.nivelActividad
            )
        }
    }
}

@Composable
fun FilaPerfil(
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titulo,
            color = TextoSecundario,
            fontSize = 14.sp
        )

        Text(
            text = valor,
            color = TextoPrincipal,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TarjetaEstado(
    estado: com.example.calculadorademasa.model.BodyFitUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CelesteClaro
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Tu estado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Interpretación de tu resultado de IMC",
                fontSize = 13.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (estado.imc != null) {
                Text(
                    text = java.lang.String.format("%.1f", estado.imc),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Text(
                    text = estado.categoria,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulBodyFit
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = estado.interpretacionImc,
                    fontSize = 13.sp,
                    color = TextoSecundario,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                LinearProgressIndicator(
                    progress = {
                        estado.progresoImc
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = AzulBodyFit,
                    trackColor = Blanco
                )
            } else {
                Text(
                    text = "Aún no has realizado tu cálculo.",
                    color = TextoSecundario,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun TarjetaHidratacion(
    estado: com.example.calculadorademasa.model.BodyFitUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AzulMuyClaro
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Hidratación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tu consumo habitual: ${estado.vasosConsumidos} vasos al día",
                fontSize = 14.sp,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (estado.vasosAgua != null) {
                Text(
                    text = "Estimación BodyFit: ${estado.vasosAgua} vasos al día",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = {
                        estado.progresoHidratacion
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = AzulBodyFit,
                    trackColor = Blanco
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (estado.vasosFaltantes > 0) {
                    Text(
                        text = "Te faltan aproximadamente ${estado.vasosFaltantes} vasos.",
                        fontSize = 13.sp,
                        color = TextoSecundario
                    )
                } else {
                    Text(
                        text = "Has alcanzado la estimación diaria.",
                        fontSize = 13.sp,
                        color = AzulBodyFit,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Text(
                    text = "Realiza el cálculo para obtener una estimación.",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }
        }
    }
}

@Composable
fun TarjetaRecomendaciones(
    recomendacion: String,
    estado: com.example.calculadorademasa.model.BodyFitUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blanco
        ),
        border = BorderStroke(
            width = 1.dp,
            color = AzulClaro
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Recomendaciones",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AzulBodyFit
            )

            Spacer(modifier = Modifier.height(14.dp))

            RecomendacionItem(
                titulo = "Alimentación",
                texto = when {
                    estado.imc == null ->
                        "Realiza tu cálculo para obtener una orientación relacionada con tu resultado."

                    estado.imc < 18.5 ->
                        "Procura incluir alimentos variados y suficientes para mantener una alimentación equilibrada."

                    estado.imc < 25.0 ->
                        "Mantén una alimentación variada que incluya frutas, verduras, proteínas y cereales."

                    estado.imc < 30.0 ->
                        "Procura mantener porciones equilibradas y aumentar el consumo de alimentos naturales."

                    else ->
                        "Prioriza alimentos naturales, verduras, frutas y porciones equilibradas."
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            RecomendacionItem(
                titulo = "Hidratación",
                texto = if (estado.vasosAgua != null) {
                    "Tu estimación es de aproximadamente ${estado.vasosAgua} vasos de agua al día."
                } else {
                    "Realiza tu cálculo para conocer una estimación de consumo de agua."
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            RecomendacionItem(
                titulo = "Actividad física",
                texto = when (estado.nivelActividad) {
                    "Sedentario" ->
                        "Procura incorporar movimiento durante el día y aumentar gradualmente tu actividad."

                    "Moderado" ->
                        "Mantén una actividad física regular de acuerdo con tus posibilidades."

                    "Activo" ->
                        "Continúa con una rutina activa y procura mantener una buena recuperación."

                    else ->
                        "Mantén hábitos de actividad física adecuados para tu rutina."
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            RecomendacionItem(
                titulo = "Peso de referencia",
                texto = if (
                    estado.pesoMinimo != null &&
                    estado.pesoMaximo != null
                ) {
                    "Para tu altura, el rango de referencia estimado es de " +
                            "${java.lang.String.format("%.1f", estado.pesoMinimo)} kg a " +
                            "${java.lang.String.format("%.1f", estado.pesoMaximo)} kg."
                } else {
                    "Realiza tu cálculo para conocer tu rango de referencia."
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (recomendacion != "") {
                Text(
                    text = "Orientación general",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulBodyFit
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = recomendacion,
                    fontSize = 14.sp,
                    color = TextoPrincipal,
                    lineHeight = 21.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Las recomendaciones son orientativas y no sustituyen la valoración de un profesional de la salud.",
                fontSize = 12.sp,
                color = TextoSecundario,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
fun RecomendacionItem(
    titulo: String,
    texto: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = titulo,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = texto,
            fontSize = 13.sp,
            color = TextoSecundario,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun BarraNavegacion(
    pantallaActual: String,
    alSeleccionar: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blanco)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BotonNavegacion(
            texto = "Calcular",
            seleccionado = pantallaActual == "calcular",
            alSeleccionar = {
                alSeleccionar("calcular")
            },
            modifier = Modifier.weight(1f)
        )

        BotonNavegacion(
            texto = "Perfil",
            seleccionado = pantallaActual == "perfil",
            alSeleccionar = {
                alSeleccionar("perfil")
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BotonNavegacion(
    texto: String,
    seleccionado: Boolean,
    alSeleccionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = alSeleccionar,
        modifier = modifier.height(46.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (seleccionado) AzulBodyFit else Blanco,
            contentColor = if (seleccionado) Blanco else AzulBodyFit
        ),
        border = if (!seleccionado) {
            BorderStroke(
                width = 1.dp,
                color = AzulBodyFit
            )
        } else {
            null
        }
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold
        )
    }
}