package com.example.coffetech.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotificationCard(
    title: String,
    description: String,
    onRejectClick: (() -> Unit)? = null,
    onAcceptClick: (() -> Unit)? = null
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Título de la notificación
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            // Descripción de la notificación
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            // Mostrar los botones si hay acciones definidas para aceptar o rechazar
            if (onRejectClick != null || onAcceptClick != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botón de Rechazar
                    onRejectClick?.let {
                        ReusableButton(
                            text = "Rechazar",
                            onClick = it,
                            buttonType = ButtonType.Red
                        )
                    }
                    // Botón de Aceptar
                    onAcceptClick?.let {
                        ReusableButton(
                            text = "Aceptar",
                            onClick = it,
                            buttonType = ButtonType.Green
                        )
                    }
                }
            }
        }
    }
}
