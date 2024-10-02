package com.example.coffetech.view.Plot
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.common.VarietyCoffeeDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Plot.EditPlotInformationViewModel
import com.example.coffetech.viewmodel.farm.FarmEditViewModel

@Composable
fun EditPlotInformationView(
    navController: NavController,
    viewModel: EditPlotInformationViewModel = viewModel()
) {
    val context = LocalContext.current

    // Obtener los estados del ViewModel
    val plotName by viewModel.plotName.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Fondo oscuro
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f) // Haz que el contenedor ocupe el 95% del ancho de la pantalla
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Añadir padding interno
            ) {
                // Botón de cerrar o volver (BackButton)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    BackButton(
                        navController = navController,
                        modifier = Modifier.size(32.dp) // Tamaño más manejable
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título de la pantalla
                Text(
                    text = "Editar Información",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                    fontSize = 25.sp,
                    color = Color(0xFF49602D),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre de lote
                ReusableTextField(
                    value = plotName,
                    onValueChange = { },
                    placeholder = "Nombre del lote",
                    modifier = Modifier.fillMaxWidth(), // Asegurar que ocupe todo el ancho disponible
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Variedad cafe
               /* VarietyCoffeeDropdown(
                    selectedVariety = selectedVariety,
                    onUnitChange = {},
                    varieties = plotCoffeeVariety,
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )*/


                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar
                ReusableButton(
                    text = if (isLoading) "Guardando..." else "Guardar",
                    onClick = {},
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp) // Ajuste de tamaño del botón
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Red,
                    enabled = hasChanges && !isLoading
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EditPlotInformationViewPreview() {
    CoffeTechTheme {
        EditPlotInformationView(
            navController = NavController(LocalContext.current)
        )
    }
}