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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.navigation.NavController
import androidx.compose.ui.zIndex
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
                    .offset(x = -10.dp)
                    .size(20.dp)
                    .background(Color(0xFFB31D34), shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "Editar Información General",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
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
                    .size(20.dp)
                    .offset(x = -10.dp)
                    .background(Color(0xFFB31D34), shape = CircleShape,)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus_icon),
                    contentDescription = "Agregar Colaborador",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
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
//FARM EDIT COMMON COMPOSABLES-----
//FARM EDIT COMMON COMPOSABLES-----
//FARM EDIT COMMON COMPOSABLES-----

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val cornerRadius = 4.dp

    Column(modifier = modifier) {
        Text(
            text = label,
            fontWeight = FontWeight.W300,
            fontSize = 22.sp,
            color = Color.Black, // Color verde
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .padding(top = 9.dp, bottom = 9.dp)
                .size(width = 288.dp, height = 50.dp)
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFA6A6A6), shape = RoundedCornerShape(cornerRadius))
                .background(Color(0xFFA6A6A6), RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Gray.copy(alpha = 0.6f), // Texto más claro cuando está deshabilitado
                disabledPlaceholderColor = Color.Gray.copy(alpha = 0.6f) // Placeholder más claro cuando está deshabilitado
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
                .padding(bottom = 5.dp)
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
                        .padding(3.dp)
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
                .widthIn(max = 200.dp)
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(
                        text = unit,
                        fontSize = 14.sp,
                        color = Color.Black,
                    ) },
                    onClick = {
                        onUnitChange(unit)
                        expanded = false
                    },
                    modifier = Modifier.padding(vertical = 0.dp), // Puedes ajustar este valor para reducir el espaciado
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
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
            .wrapContentWidth()
            .padding(bottom = 15.dp)
            .padding(horizontal = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(25.dp)) // Fondo blanco con esquinas redondeadas
            .border(1.dp, Color(0xD7FFFEFE), shape = RoundedCornerShape(25.dp)) // Borde gris alrededor del fondo
            .size(width = 150.dp, height = 40.dp) // Tamaño del área del botón
    ) {
        OutlinedButton(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier
                .fillMaxWidth(), // Ajusta el tamaño para llenar el área blanca
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White),
            ) {
                Text(
                    text = selectedRole ?: "Todos los roles", // Muestra "Todos los roles" si es null
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

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(Color.White),
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





@Composable
fun BackButton(navController: NavController, modifier: Modifier) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(1.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.close), // Usa tu ícono de retroceso
            contentDescription = "Back",
            tint = Color.Black
        )
    }
}
