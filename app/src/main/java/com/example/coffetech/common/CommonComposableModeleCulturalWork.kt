// GenericDropdown.kt
package com.example.coffetech.view.CulturalWorkTask

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.viewmodel.cultural.CulturalWorkTask
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
fun CulturalWorkTaskGeneralCard(
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



@Composable
fun TypeCulturalWorkDropdown(
    selectedCulturalWork: String,
    cultural_work: List<String>,  // Parámetro para unidades dinámicas
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter,
    onTypeCulturalWorkChange: (String) -> Unit,
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
                        text = selectedCulturalWork,
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
            cultural_work.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(
                        text = unit,
                        fontSize = 14.sp,
                        color = Color.Black,
                    ) },
                    onClick = {
                        onTypeCulturalWorkChange(unit)
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
fun DateDropdown(
    selectedDate: String,
    dates: List<String>,  // Parámetro para unidades dinámicas
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter,
    onDateChange: (String) -> Unit,
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
                        text = selectedDate,
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
            dates.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(
                        text = unit,
                        fontSize = 14.sp,
                        color = Color.Black,
                    ) },
                    onClick = {
                        onDateChange(unit)
                        expanded = false
                    },
                    modifier = Modifier.padding(vertical = 0.dp), // Puedes ajustar este valor para reducir el espaciado
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComposable(
    label: String,
    selectedDate: String?,
    onDateSelected: (String) -> Unit,
    onClearDate: (() -> Unit)? = null,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Formateador de fecha
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // Convertir la fecha seleccionada a Calendar si no es nula
    selectedDate?.let {
        try {
            val date = dateFormat.parse(it)
            date?.let { parsedDate ->
                calendar.time = parsedDate
            }
        } catch (e: Exception) {
            // Manejar el error de parsing si es necesario
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    // Estilos similares a ReusableTextField
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedDate ?: "",
            onValueChange = {},
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) { if (enabled) showDialog = true }, // Condicional
            enabled = false, // Deshabilitar la edición manual
            trailingIcon = {
                Row {
                    // Ícono de eliminar, visible solo si hay una fecha seleccionada y se ha proporcionado onClearDate
                    if (!selectedDate.isNullOrEmpty() && onClearDate != null) {
                        IconButton(
                            onClick = { onClearDate() },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Eliminar fecha",
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)

                            )
                        }
                    }
                }
            },
            isError = errorMessage != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedBorderColor = if (errorMessage != null) Color.Red else Color(0xFF5D8032),
                unfocusedBorderColor = if (errorMessage != null) Color.Red else Color.Gray,
                disabledBorderColor = Color.Gray,
                containerColor = Color.White,
                errorBorderColor = Color.Red
            ),
            shape = RoundedCornerShape(4.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 16.sp
            )
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        if (showDialog) {
            android.app.DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(year, month, dayOfMonth)
                    val selected = dateFormat.format(calendar.time)
                    onDateSelected(selected)
                    showDialog = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                setOnCancelListener {
                    showDialog = false
                }
                show()
            }
        }
    }
}

@Composable
fun ReusableAlertDialog(
    title: String,
    description: String,
    confirmButtonText: String,
    cancelButtonText: String,
    isLoading: Boolean = false,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismissRequest: () -> Unit,
    image: Painter // El parámetro de la imagen se agrega aquí
) {
    AlertDialog(
        containerColor = Color.White,
        modifier = Modifier.background(Color.Transparent),
        onDismissRequest = { onDismissRequest() },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen en la parte superior
                Icon(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 207.dp, height = 160.dp)
                        .padding(bottom = 6.dp), // Espaciado debajo de la imagen
                    tint = Color.Unspecified // Evitar que se tiña la imagen
                )

                // Título
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB31D34), // Color rojo para el título
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        text = {
            // Descripción del diálogo
            Text(
                text = description,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Espaciado interno del texto
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ReusableButton(
                        text = if (isLoading) "$confirmButtonText..." else confirmButtonText,
                        onClick = onConfirmClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.7f), // Ajusta el ancho del botón
                        buttonType = ButtonType.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
                    ReusableButton(
                        text = cancelButtonText,
                        onClick = onCancelClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.7f), // Ajusta el ancho del botón
                        buttonType = ButtonType.Green
                    )
                }
            }
        }
    )
}


