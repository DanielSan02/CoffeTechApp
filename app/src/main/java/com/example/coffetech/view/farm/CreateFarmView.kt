package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.LabeledTextField
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.farm.CreateFarmViewModel

@Composable
fun CreateFarmView(
    navController: NavController,
    viewModel: CreateFarmViewModel = viewModel()
) {
    val farmName by viewModel.farmName.collectAsState()
    val farmArea by viewModel.farmArea.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Fondo oscuro
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .offset(x = -26.dp, y = -20.dp)
                // Ajuste para evitar superposición con el botón
            ) {


                BackButton(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Crear Finca",
                    fontWeight = FontWeight.W600,
                    fontSize = 25.sp,
                    color = Color(0xFF49602D),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Nombre de finca
                LabeledTextField(
                    label = "Nombre",
                    value = farmName,
                    onValueChange = { viewModel.onFarmNameChange(it) },
                    placeholder = "Nombre de la finca",
                    modifier = Modifier
                        .fillMaxWidth()

                )

                // Área de la finca y unidad
                Column(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LabeledTextField(
                        label = "Área",
                        value = farmArea,
                        onValueChange = { viewModel.onFarmAreaChange(it) },
                        placeholder = "Área de la finca",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Unidad de medida
                    UnitDropdown(
                        selectedUnit = selectedUnit,
                        onUnitChange = { viewModel.onUnitChange(it) },
                        expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                        arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    )
                }

                // Botón para crear finca
                Column(
                    modifier = Modifier
                        .padding(top = 35.dp, bottom = 50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ReusableButton(
                        text = "Crear",
                        onClick = { viewModel.onCreate(navController) },
                        modifier = Modifier
                            .size(width = 120.dp, height = 40.dp)
                            .padding(bottom = 3.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateFarmViewPreview() {
    CoffeTechTheme {
        CreateFarmView(navController = NavController(LocalContext.current))
    }
}
