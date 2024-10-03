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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R

@Composable
fun GeneralPlotInfoCard(
    plotName: String,
    plotCoffeeVariety: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                    text = "Información General",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = plotName,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = plotCoffeeVariety,
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
fun PlotFaseCard(
    faseName: String,
    initialDate: String,
    endDate: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                    text = "Fase Actual",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = faseName,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = initialDate,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Text(
                    text = endDate,
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
                    contentDescription = "Editar Informacion de fase actual",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }

        }
    }
}


@Composable
fun PlotUbicationCard(
    coordinatesUbication: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                    text = "Ubicacion",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = coordinatesUbication,
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
                    contentDescription = "Editar Informacion de fase actual",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }

        }
    }
}





@Composable
fun ActionCard(
    buttonText: String, // Nuevo parámetro para el texto del botón
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(159.dp)
            .padding(8.dp)
            .background(Color(0xFFE52542), shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick) // Hacer el botón clickable
    ) {

        Icon(
            painter = painterResource(id = R.drawable.vector_3_),
            contentDescription = "Icono de acción",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
        )


        Text(
            text = buttonText,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}






@Composable
fun VarietyCoffeeDropdown(
    selectedVariety: String,
    varieties: List<String>,  // Parámetro para unidades dinámicas
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
                        text = selectedVariety,
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



        @Composable
        fun CircularIconButton(
            modifier: Modifier = Modifier,
            iconResId: Int,
            iconSize: Int = 24,
            backgroundColor: Color = Color.Gray,
            iconTint: Color = Color.White,
            text: String,
            textColor: Color = Color.Gray,
            textSize: Int = 12,
            onClick: () -> Unit
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(8.dp)
            ) {
                // Box containing the icon
                Box(
                    modifier = Modifier
                        .size(56.dp) // Set the desired size for the Box
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .clickable(onClick = onClick),
                    contentAlignment = Alignment.Center
                ) {
                    // Icon inside the Box
                    Icon(
                        painter = painterResource(iconResId),
                        contentDescription = text,
                        modifier = Modifier.size(iconSize.dp), // Set the size for the icon
                        tint = iconTint // Change the color of the icon
                    )
                }
                // Text below the Box
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    fontSize = textSize.sp,
                    color = textColor
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .widthIn(max = 200.dp)
        ) {
            varieties.forEach { unit ->
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