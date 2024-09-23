package com.example.coffetech.view.Notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.common.ReusableButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
//import com.example.coffetech.viewmodel.Notification.NotificationViewModel
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.NotificationCard

@Composable
fun NotificationView(
    modifier: Modifier = Modifier,
    navController: NavController,
    //viewModel: NotificationViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .background(Color(0xFFF2F2F2))
            .fillMaxSize()
            .statusBarsPadding() // Ajusta el padding para la barra de estado
            .navigationBarsPadding() // Ajusta el padding para la barra de navegación
    ) {
        // Top bar con botón de retroceso
        TopBarWithBackArrow(
            onBackClick = { navController.navigate(Routes.StartView) },
            title = "Notificaciones"
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Notificaciones
            NotificationCard(
                title = "Nueva Invitación",
                description = "nameUser te ha invitado a colaborar como rol en la finca nameFarm",
                onRejectClick = { /* Acción de rechazar */ },
                onAcceptClick = { /* Acción de aceptar */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotificationCard(
                title = "Solicitud Aceptada",
                description = "nameUser ha aceptado tu solicitud para colaborar en nameFarm. Programa sus labores ahora."
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotificationCard(
                title = "Solicitud Rechazada",
                description = "nameUser ha rechazado tu solicitud para colaborar en nameFarm. Reenvía la invitación.",
                onResendClick = { /* Acción de reenviar */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationViewPreview() {
    CoffeTechTheme {
        val navController = NavController(LocalContext.current)

        NotificationView(
            navController = navController
        )
    }
}
