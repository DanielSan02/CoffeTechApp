package com.example.coffetech.view.Plot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.viewmodel.Plot.EditMapPlotViewModel

@Composable
fun EditLocationView(
    navController: NavController?, // Lo marcamos como opcional para no necesitar un NavController en Preview
    viewModel: EditMapPlotViewModel = viewModel()
) {
    // Estados para manejar los campos de texto
    var radius by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("km") } // Unidad por defecto

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))  // Fondo oscuro
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(Color.White, RoundedCornerShape(19.dp))
                .padding(horizontal = 20.dp, vertical = 90.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la vista
                Text(
                    text = "Editar ubicación",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF606C38),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Simular un mapa (aquí podrías integrar un mapa real)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray),  // Simulación del mapa
                    contentAlignment = Alignment.Center
                ) {
                    Text("Mapa (Simulación)")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para el radio
                ReusableTextField(
                    value = radius,
                    onValueChange = { radius = it },
                    placeholder = "Radio",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))



                Text("Unidad: $unit") // Muestra la unidad seleccionada
                Spacer(modifier = Modifier.height(24.dp))

                // Botón Guardar
                Button(
                    onClick = {
                        // Lógica para guardar los datos
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D))  // Color verde
                ) {
                    Text("Guardar")
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMapPlotViewPreview() {
    CoffeTechTheme {
        EditLocationView(navController = null)  // Para la previsualización no necesitamos un NavController real
    }
}


