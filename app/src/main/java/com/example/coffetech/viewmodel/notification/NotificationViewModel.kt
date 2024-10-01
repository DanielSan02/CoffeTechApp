import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.coffetech.model.ApiResponse
import com.example.coffetech.model.Notification
import com.example.coffetech.model.NotificationResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel : ViewModel() {

    // Estado de las notificaciones
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    // Estado de error
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Función para cargar las notificaciones desde el servidor
    fun loadNotifications(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            _errorMessage.value = "No se encontró el token de sesión."
            _isLoading.value = false
            return
        }

        _isLoading.value = true

        RetrofitInstance.api.getNotifications(sessionToken).enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _notifications.value = it.data
                    } ?: run {
                        _errorMessage.value = "No notifications available."
                    }
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Error de conexión: ${t.message}"
            }
        })
    }

    // Función para aceptar o rechazar una invitación
    fun respondToInvitation(
        context: Context,
        invitationId: Int,
        action: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            onFailure("No se encontró el token de sesión.")
            return
        }

        RetrofitInstance.api.respondInvitation(invitationId, action, sessionToken)
            .enqueue(object : Callback<ApiResponse<Any>> {
                override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.status == "success") {
                                Toast.makeText(context, "Acción realizada con éxito", Toast.LENGTH_SHORT).show()
                                onSuccess()
                            } else {
                                onFailure(it.message)
                            }
                        } ?: run {
                            onFailure("Respuesta vacía del servidor.")
                        }
                    } else {
                        onFailure("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                    onFailure("Error de conexión: ${t.message}")
                }
            })
    }
}
