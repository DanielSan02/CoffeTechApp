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
import com.example.coffetech.common.LabeledTextField
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.farm.FarmEditViewModel

@Composable
fun FarmEditView(
    navController: NavController,
    viewModel: FarmEditViewModel = viewModel()
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Información de Finca",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF49602D),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Nombre de fincq
                LabeledTextField(
                    label = "Nombre",
                    value = farmName,
                    onValueChange = { viewModel.onFarmNameChange(it) },
                    placeholder = "Nombre de la finca",
                    modifier = Modifier.fillMaxWidth()
                )

                // Area de la finca
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

                    // Unidad de medida
                   /* UnitDropdown(
                        selectedUnit = selectedUnit,
                        onUnitChange = { viewModel.onUnitChange(it) },
                        expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                        arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    )*/

                }
                Column(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Guardar
                    ReusableButton(
                        text = "Guardar",
                        onClick = { viewModel.onSave(navController) },
                        modifier = Modifier
                            .size(width = 120.dp, height = 40.dp)
                            .padding(vertical = 3.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D))
                    )

                    //Eliminar
                    ReusableButton(
                        text = "Eliminar",
                        onClick = { viewModel.onDelete(navController) },
                        modifier = Modifier
                            .size(width = 120.dp, height = 35.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB31D34))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmEditViewPreview() {
    CoffeTechTheme {
        FarmEditView(navController = NavController(LocalContext.current))
    }
}