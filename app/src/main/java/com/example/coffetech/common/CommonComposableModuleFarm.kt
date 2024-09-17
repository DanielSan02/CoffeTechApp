package com.example.coffetech.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
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
        mainButtonIcon = painterResource(id = R.drawable.plus_icon),
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

@Composable
fun FarmItemCard(
    farmName: String,
    farmRole: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF86B049), RoundedCornerShape(12.dp)) // Fondo verde con bordes redondeados
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = farmName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = farmRole,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 1
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
    units: List<String>,  // Parámetro para unidades dinámicas
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter,
    onUnitChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 13.dp)
                .padding(horizontal = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(25.dp))
                .border(1.dp, Color(0xD7FFFEFE), shape = RoundedCornerShape(25.dp))
                .size(width = 95.dp, height = 40.dp)
        ) {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF49602D)
                ),
                contentPadding = PaddingValues(start = 4.dp, end = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedUnit,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis, // Manejo del desbordamiento con puntos suspensivos
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = if (expanded) expandedArrowDropUp else arrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF5D8032)
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
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

@Composable
fun RoleDropdown(
    selectedRole: String?,  // Cambiado a String? para aceptar valores nulos
    onRoleChange: (String?) -> Unit, // onRoleChange ahora acepta String? para manejar la deselección
    roles: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter
) {
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(50.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(50.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedButton(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            border = null,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black,
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedRole ?: "Todos los roles", // Muestra "Todos los roles" si es null
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Icon(
                    painter = if (expanded) expandedArrowDropUp else arrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFF8FB399)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Opción para mostrar todas las fincas
            DropdownMenuItem(
                text = { Text("Todos los roles") },
                onClick = {
                    onRoleChange(null) // Pasa null para quitar el filtro de roles
                    onExpandedChange(false)
                }
            )

            // Opciones de roles reales
            roles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        onRoleChange(role)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}





