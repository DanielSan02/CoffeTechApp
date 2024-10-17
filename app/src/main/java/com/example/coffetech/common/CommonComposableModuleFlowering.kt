package com.example.coffetech.common

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.model.Flowering
import com.example.coffetech.model.Plot
import com.example.coffetech.ui.theme.CoffeTechTheme

@Composable
fun FloweringGeneralInfoCard(
    flowering_type_name: String,
    status: String,
    flowering_date: String,
    onEditClick: () -> Unit,
    onInfoClick: () -> Unit,
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
            Column(
                modifier = Modifier.weight(1f) // Permite que el texto ocupe todo el espacio disponible
            ) {
                Text(
                    text = flowering_type_name,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = status,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = flowering_date,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre los botones
            ) {
                // Botón de información (verde)

                // Botón de edición (rojo)
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .offset(-9.dp)
                        .size(36.dp) // Tamaño del botón
                        .background(Color(0xFFB31D34), shape = CircleShape) // Rojo
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon), // Usa el icono de edición
                        contentDescription = "Editar Información General",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onInfoClick,
                    modifier = Modifier
                        .size(36.dp) // Tamaño del botón
                        .background(Color(0xFF8AA72A), shape = CircleShape) // Verde
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.info_icon), // Usa el icono de información
                        contentDescription = "Información",
                        tint = Color.White,
                        modifier = Modifier.size(29.dp)
                    )
                }
            }
        }
    }
}



@Composable
fun FloweringItemCard(
    flowering_type_name: String,
    status: String,
    harvest_date: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)) // Fondo verde con bordes redondeados
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = flowering_type_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = status,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = harvest_date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 1
            )
        }
    }
}

@Composable
fun FloweringList(
    flowerings: List<Flowering>, // Lista de objetos Lote
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Historial de Floraciones",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        flowerings.forEach { flowering ->
            Spacer(modifier = Modifier.height(8.dp))
            FloweringItemCard(
                flowering_type_name = flowering.flowering_type_name,
                status = flowering.status,
                harvest_date = flowering.harvest_date,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { } // Pasa el objeto Lote al hacer clic
            )
        }
    }
}

@Composable
fun FloweringNameDropdown(
    selectedFloweringName: String?,  // Cambiado a String? para aceptar valores nulos
    onFloweringNameChange: (String?) -> Unit, // onRoleChange ahora acepta String? para manejar la deselección
    flowerings: List<String>,
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
            .background(Color.White, shape = RoundedCornerShape(20.dp)) // Fondo blanco con esquinas redondeadas
            .size(width = 200.dp, height = 32.dp) // Tamaño del área del botón
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
                    text = selectedFloweringName ?: "General", // Muestra "Todos los roles" si es null
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
                    onFloweringNameChange(null) // Pasa null para quitar el filtro de roles
                    onExpandedChange(false)
                }
            )

            // Opciones de roles reales
            flowerings.forEach { flowering ->
                DropdownMenuItem(
                    text = { Text(
                        flowering,
                        color = Color.Black) },
                    onClick = {
                        onFloweringNameChange(flowering)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun FloweringNameDropdown(
    selectedFloweringName: String,
    flowerings: List<String>,  // Parámetro para unidades dinámicas
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter,
    onFloweringNameChange: (String) -> Unit,
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
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .size(width = 300.dp, height = 56.dp)
        ) {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF49602D)
                ),
                contentPadding = PaddingValues(start = 10.dp, end = 4.dp)
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
                        text = selectedFloweringName,
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
            flowerings.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(
                        text = unit,
                        fontSize = 14.sp,
                        color = Color.Black,
                    ) },
                    onClick = {
                        onFloweringNameChange(unit)
                        expanded = false
                    },
                    modifier = Modifier.padding(vertical = 0.dp), // Puedes ajustar este valor para reducir el espaciado
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloweringGeneralInfoCardPreview() {
    CoffeTechTheme {
        FloweringGeneralInfoCard(
            flowering_type_name = "Principal",
            status= "Activa",
            flowering_date = "15-08-2024",
            onEditClick = {},
            onInfoClick = {}
        )
    }
}