// GenericDropdown.kt
package com.example.coffetech.view.CulturalWorkTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R
import com.example.coffetech.viewmodel.cultural.CulturalWorkTask

// GenericDropdown.kt
@Composable
fun GenericDropdown(
    modifier: Modifier = Modifier, // Añadido
    selectedOption: String?, // Valor seleccionado, puede ser null
    onOptionSelected: (String?) -> Unit, // Callback cuando se selecciona una opción
    options: List<String>, // Lista de opciones
    expanded: Boolean, // Estado de expansión del dropdown
    onExpandedChange: (Boolean) -> Unit, // Callback para cambiar el estado de expansión
    label: String, // Texto de etiqueta o placeholder
    expandedArrowDropUp: Painter, // Icono cuando el dropdown está expandido
    arrowDropDown: Painter // Icono cuando el dropdown está cerrado
) {
    Box(
        modifier = modifier // Aplicar el modifier aquí
            .wrapContentWidth()
            .padding(bottom = 15.dp)
            .padding(horizontal = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedButton(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = selectedOption ?: label,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = if (expanded) expandedArrowDropUp else arrowDropDown,
                    contentDescription = if (expanded) "Collapse dropdown" else "Expand dropdown",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF5D8032)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(Color.White)
        ) {
            // Opción para deseleccionar (mostrar todas las opciones)
            DropdownMenuItem(
                text = { Text(text = "Todos") },
                onClick = {
                    onOptionSelected(null)
                    onExpandedChange(false)
                }
            )

            // Opciones dinámicas
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = Color.Black) },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
@Composable
fun CulturalWorkTaskCard(
    task: CulturalWorkTask,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),  // Espaciado alrededor de la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Fondo blanco
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre de la tarea
            Text(
                text = task.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Asignado a
            Text(
                text = "Asignado a: ${task.assignedToName}",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Estado
            Text(
                text = "Estado: ${task.state}",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Fecha formateada
            val formattedDate = java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(task.date))
            Text(
                text = "Fecha: $formattedDate",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
