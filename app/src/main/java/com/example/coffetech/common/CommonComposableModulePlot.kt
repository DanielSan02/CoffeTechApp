package com.example.coffetech.common


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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