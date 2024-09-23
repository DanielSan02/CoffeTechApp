package com.example.coffetech.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
    onAcceptClick: (() -> Unit)? = null,
    onResendClick: (() -> Unit)? = null
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            onRejectClick?.let {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ReusableButton(
                        text = "Rechazar",
                        onClick = it,
                        buttonType = ButtonType.Red
                    )
                    onAcceptClick?.let { acceptClick ->
                        ReusableButton(
                            text = "Aceptar",
                            onClick = acceptClick,
                            buttonType = ButtonType.Green
                        )
                    }
                }
            }
            onResendClick?.let {
                ReusableButton(
                    text = "Reenviar",
                    onClick = it,
                    buttonType = ButtonType.Green
                )
            }
        }
    }
}