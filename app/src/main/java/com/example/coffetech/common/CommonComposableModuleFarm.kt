package com.example.coffetech.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R

//FARM INFORMATION COMMONS COMPOSABLES---------------------------------
//FARM INFORMATION COMMONS COMPOSABLES---------------------------------
//FARM INFORMATION COMMONS COMPOSABLES---------------------------------
//FARM INFORMATION COMMONS COMPOSABLES---------------------------------
@Composable
fun SelectedRoleDisplay(
    roleName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Su rol es: ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            text = roleName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF49602D), // Subrayado azul o puedes cambiar el color
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun GeneralInfoCard(
    farmName: String,
    farmArea: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Información General",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = farmName,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text(
                    text = farmArea,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFB31D34), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Editar Información General",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun CollaboratorsCard(
    collaboratorName: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Colaboradores",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = collaboratorName,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFB31D34), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus_icon),
                    contentDescription = "Agregar Colaborador",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun CustomFloatingActionButton(
    onAddLoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButtonGroup(
        onMainButtonClick = onAddLoteClick, // Navegar a la vista de agregar lote
        onSubButton1Click = { /* Lógica adicional si es necesario */ },
        onSubButton2Click = { /* Lógica adicional si es necesario */ },
        subButton1Icon = painterResource(id = R.drawable.edit_icon),
        subButton2Icon = painterResource(id = R.drawable.plus_icon),
        mainButtonIcon = painterResource(id = R.drawable.plus_icon),
        expandedMainButtonIcon = painterResource(id = R.drawable.closefloat_icon)
    )
}

@Composable
fun LoteItemCard(
    loteName: String,
    loteDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF86B049), RoundedCornerShape(12.dp)) // Fondo verde con bordes redondeados
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = loteName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = loteDescription,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 1
            )
        }
    }
}

@Composable
fun LotesList(
    lotes: List<Pair<String, String>>, // Lista de lotes (nombre, descripción)
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Lotes",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        lotes.forEach { lote ->
            Spacer(modifier = Modifier.height(8.dp))
            LoteItemCard(
                loteName = lote.first,
                loteDescription = lote.second,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


//FARM EDIT COMMON COMPOSABLES-----

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF49602D), // Color verde
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun UnitDropdown(
    selectedUnit: String,
    onUnitChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("Metros cuadrados", "Kilometros", "Hectáreas") // Lista de unidades

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF49602D)
            )
        ) {
            Text(text = selectedUnit)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Seleccionar unidad")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(text = unit) },
                    onClick = {
                        onUnitChange(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
