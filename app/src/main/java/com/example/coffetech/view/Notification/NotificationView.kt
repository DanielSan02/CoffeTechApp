import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.common.NotificationCard
import com.example.coffetech.ui.theme.CoffeTechTheme
@Composable
fun NotificationView(
    navController: NavController,
    viewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current

    // Cargar notificaciones cuando el Composable se crea
    LaunchedEffect(Unit) {
        viewModel.loadNotifications(context)
    }

    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopBarWithBackArrow(
            onBackClick = { navController.popBackStack() },
            title = "Notificaciones"
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            errorMessage.isNotEmpty() -> {
                Text(text = errorMessage, color = Color.Red)
            }
            notifications.isNotEmpty() -> {
                notifications.forEach { notification ->
                    NotificationCard(
                        title = when (notification.type) {
                            "invitacion" -> "Nueva Invitación"
                            "solicitud" -> "Solicitud Aceptada"
                            else -> "Notificación"
                        },
                        description = notification.message,
                        // Mostrar botones solo si `type` es "invitacion" y `is_responded` es `false`
                        onRejectClick = if (notification.type == "invitacion" && !notification.is_responded) {
                            {
                                viewModel.respondToInvitation(
                                    context,
                                    notification.invitation_id,
                                    "reject",
                                    onSuccess = {
                                        // Actualizar la lista de notificaciones
                                        viewModel.loadNotifications(context)
                                    },
                                    onFailure = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        } else null,
                        onAcceptClick = if (notification.type == "invitacion" && !notification.is_responded) {
                            {
                                viewModel.respondToInvitation(
                                    context,
                                    notification.invitation_id,
                                    "accept",
                                    onSuccess = {
                                        // Actualizar la lista de notificaciones
                                        viewModel.loadNotifications(context)
                                    },
                                    onFailure = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        } else null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            else -> {
                Text(text = "No tienes notificaciones.", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}


