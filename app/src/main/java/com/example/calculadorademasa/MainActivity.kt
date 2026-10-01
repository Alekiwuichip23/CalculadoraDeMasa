package com.example.calculadorademasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadorademasa.iu.BodyFitScreen
import com.example.calculadorademasa.ui.theme.CalculadoraDeMasaTheme
import com.example.calculadorademasa.viewmodel.BodyFitViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CalculadoraDeMasaTheme {

                val viewModel: BodyFitViewModel = viewModel()

                BodyFitScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}