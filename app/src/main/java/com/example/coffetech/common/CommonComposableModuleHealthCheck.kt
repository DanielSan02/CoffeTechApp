package com.example.coffetech.common

import androidx.compose.runtime.Composable
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DateDropdown(
    selectedDate: String?,  // Cambiado a String? para aceptar valores nulos
    onDateChange: (String?) -> Unit, // onRoleChange ahora acepta String? para manejar la deselección
    dates: List<String>,
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
            .background(
                Color.White,
                shape = RoundedCornerShape(10.dp)
            ) // Fondo blanco con esquinas redondeadas
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
                    text = selectedDate ?: "Fecha", // Muestra "Todos los roles" si es null
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
                    onDateChange(null) // Pasa null para quitar el filtro de roles
                    onExpandedChange(false)
                }
            )

            // Opciones de roles reales
            dates.forEach { date ->
                DropdownMenuItem(
                    text = { Text(
                        date,
                        color = Color.Black) },
                    onClick = {
                        onDateChange(date)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun CheckingInfoCard(
    checkingId: String,
    date: String,
    collaboratorName: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    showEditIcon: Boolean = false // Nuevo parámetro opcional para mostrar el botón
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
                    text = "Chequeo ID: $checkingId",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Fecha: $date",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Hecha por: $collaboratorName",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            if (showEditIcon) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .offset(x = -10.dp)
                        .size(20.dp)
                        .background(Color(0xFFB31D34), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "Editar Información Colaborador",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetectionResultInfoCard(
    detectionId: Int,
    nameFarm: String,
    namePlot: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF95A94B), RoundedCornerShape(16.dp))
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
                    text = "Numero de la imagen: $detectionId",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = nameFarm,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = namePlot,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = description,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Espaciado interno del texto
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CheckingInfoCardPreview() {
    CoffeTechTheme {
        CheckingInfoCard(
            checkingId= "3",
            date= "24-02-34",
            collaboratorName = "Nombre",
            onEditClick = {},
            showEditIcon = true,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetectionResultInfoCardPreview() {
    CoffeTechTheme {
        DetectionResultInfoCard(
            detectionId = 4,
            nameFarm = "Finca 1",
            namePlot= "Lote 1",
            description = "Lorem ipsum dolor sit amet consectetur." +
                    "Fames molestie magna massa turpis. Eros feugiat nullam elit semper. " +
                    "Sollicitudin urna sapien risus dignissim velit condimentum fringilla vitae sit." +
                    " In metus felis magna nullam viverra tincidunt at a accumsan. Scelerisque tempus vulputate" +
                    "fermentum vulputate. Nibh morbi purus eget luctus.",
        )
    }
}

