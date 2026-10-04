package com.example.calculadorademasa.iu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculadorademasa.R
import com.example.calculadorademasa.model.BodyFitUiState
import com.example.calculadorademasa.ui.theme.AzulBodyFit
import com.example.calculadorademasa.ui.theme.AzulBoton
import com.example.calculadorademasa.ui.theme.AzulClaro
import com.example.calculadorademasa.ui.theme.AzulMuyClaro
import com.example.calculadorademasa.ui.theme.AzulOscuro
import com.example.calculadorademasa.ui.theme.Blanco
import com.example.calculadorademasa.ui.theme.CelesteClaro
import com.example.calculadorademasa.ui.theme.FondoBodyFit
import com.example.calculadorademasa.ui.theme.RojoError
import com.example.calculadorademasa.ui.theme.TextoPrincipal
import com.example.calculadorademasa.ui.theme.TextoSecundario
import com.example.calculadorademasa.viewmodel.BodyFitViewModel

@Composable
fun BodyFitScreen(
    viewModel: BodyFitViewModel,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoBodyFit)
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
                    alCambiarPeso = viewModel::cambiarPeso,
                    alCambiarAltura = viewModel::cambiarAltura,
                    alCambiarEdad = viewModel::cambiarEdad,
                    alCambiarSexo = viewModel::cambiarSexo,
                    alCambiarActividad = viewModel::cambiarNivelActividad,
                    alCambiarVasos = viewModel::cambiarVasosConsumidos,
                    alCalcular = viewModel::calcular
                )
            } else {
                PantallaPerfil(
                    estado = estado,
                    alEditar = { viewModel.cambiarPantalla("calcular") }
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
    estado: BodyFitUiState,
    alCambiarPeso: (String) -> Unit,
    alCambiarAltura: (String) -> Unit,
    alCambiarEdad: (String) -> Unit,
    alCambiarSexo: (String) -> Unit,
    alCambiarActividad: (String) -> Unit,
    alCambiarVasos: (String) -> Unit,
    alCalcular: () -> Unit
) {
    EncabezadoCalculadora()

    Spacer(modifier = Modifier.height(28.dp))

    CampoPeso(
        valor = estado.peso,
        alCambiar = alCambiarPeso
    )

    Spacer(modifier = Modifier.height(16.dp))

    CampoAltura(
        valor = estado.altura,
        alCambiar = alCambiarAltura
    )

    Spacer(modifier = Modifier.height(16.dp))

    CampoEdad(
        valor = estado.edad,
        alCambiar = alCambiarEdad
    )

    Spacer(modifier = Modifier.height(20.dp))

    SelectorSexo(
        sexoSeleccionado = estado.sexo,
        alSeleccionar = alCambiarSexo
    )

    Spacer(modifier = Modifier.height(20.dp))

    SelectorActividad(
        actividadSeleccionada = estado.nivelActividad,
        alSeleccionar = alCambiarActividad
    )

    Spacer(modifier = Modifier.height(20.dp))

    CampoAgua(
        valor = estado.vasosConsumidosEntrada,
        alCambiar = alCambiarVasos
    )

    Spacer(modifier = Modifier.height(24.dp))

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
            imcTexto = estado.imcTexto,
            categoria = estado.categoria,
            rangoPesoTexto = estado.rangoPesoTexto,
            aguaTexto = estado.aguaTexto,
            vasosAguaTexto = estado.vasosAguaTexto,
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
    valor: String,
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
            value = valor,
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
    imcTexto: String,
    categoria: String,
    rangoPesoTexto: String,
    aguaTexto: String,
    vasosAguaTexto: String,
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
                text = imcTexto,
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

            Spacer(modifier = Modifier.height(20.dp))

            FilaResultado(
                titulo = "Peso de referencia",
                valor = rangoPesoTexto
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilaResultado(
                titulo = "Agua aproximada",
                valor = aguaTexto
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilaResultado(
                titulo = "Consumo aproximado",
                valor = vasosAguaTexto
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

@Composable
fun PantallaPerfil(
    estado: BodyFitUiState,
    alEditar: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AvatarPerfil(
            sexo = estado.sexo
        )

        Spacer(modifier = Modifier.height(12.dp))
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
            recomendacion = estado.recomendacion
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = alEditar,
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
    estado: BodyFitUiState
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
                valor = estado.edadPerfilTexto
            )

            FilaPerfil(
                titulo = "Sexo",
                valor = estado.sexo
            )

            FilaPerfil(
                titulo = "Peso",
                valor = estado.pesoPerfilTexto
            )

            FilaPerfil(
                titulo = "Altura",
                valor = estado.alturaPerfilTexto
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
fun AvatarPerfil(
    sexo: String
) {
    val avatar = if (sexo == "Mujer") {
        R.drawable.avatar_mujer
    } else {
        R.drawable.avatar_hombre
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = "Avatar de perfil",
            modifier = Modifier.size(120.dp)
        )
    }
}
@Composable
fun TarjetaEstado(
    estado: BodyFitUiState
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

            Spacer(modifier = Modifier.height(12.dp))

            if (estado.imc != null) {
                Text(
                    text = "Interpretación de tu resultado de IMC",
                    fontSize = 13.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = estado.imcTexto,
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
    estado: BodyFitUiState
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
                text = estado.consumoHabitualTexto,
                fontSize = 14.sp,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (estado.vasosAgua != null) {
                Text(
                    text = estado.estimacionVasosTexto,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { estado.progresoHidratacion },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = AzulBodyFit,
                    trackColor = Blanco
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = estado.mensajeHidratacion,
                    fontSize = 13.sp,
                    color = TextoSecundario
                )
            } else {
                Text(
                    text = estado.mensajeHidratacion,
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }
        }
    }
}

@Composable
fun TarjetaRecomendaciones(
    recomendacion: String
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = recomendacion,
                fontSize = 14.sp,
                color = TextoPrincipal,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

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
