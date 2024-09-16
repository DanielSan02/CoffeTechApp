// Navigation.kt

package com.example.coffetech.navigation

import android.content.Context // Importar el Context correcto
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coffetech.Routes.Routes
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.Auth.LoginView
import com.example.coffetech.view.Auth.RegisterView
import com.example.coffetech.view.Auth.ForgotPasswordView
import com.example.coffetech.view.Auth.ConfirmTokenForgotPasswordView
import com.example.coffetech.view.Auth.VerifyAccountView
import com.example.coffetech.view.Auth.AlertSendView
import com.example.coffetech.view.Auth.ChangePasswordView
import com.example.coffetech.view.Auth.NewPasswordView
import com.example.coffetech.view.Auth.ProfileView
import com.example.coffetech.view.Auth.StartView
import com.example.coffetech.view.farm.FarmEditView
import com.example.coffetech.view.farm.FarmInformationView
import com.example.coffetech.view.farm.FarmView

@Composable
fun AppNavHost(context: Context) {
    val navController = rememberNavController()

    val sharedPreferencesHelper = SharedPreferencesHelper(context)

    // Verificar si el usuario está logueado
    val isLoggedIn = sharedPreferencesHelper.isLoggedIn()

    BackHandler {
        // Si no haces nada, el gesto queda bloqueado
    }
    // Navegar a la pantalla correcta dependiendo de si el usuario está logueado o no
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.StartView else Routes.LoginView
    ) {

        composable(Routes.LoginView) {
            LoginView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.RegisterView) {
            RegisterView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.ForgotPasswordView) {
            ForgotPasswordView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.ConfirmTokenForgotPasswordView) {
            ConfirmTokenForgotPasswordView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.VerifyAccountView) {
            VerifyAccountView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.AlertSendView) {
            AlertSendView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(
            route = "${Routes.NewPasswordView}/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recuperar el token del backStackEntry
            val token = backStackEntry.arguments?.getString("token") ?: ""

            // Llamar a la vista NewPasswordView con el token obtenido
            NewPasswordView(navController = navController, token = token)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.FarmView) {
            FarmView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.FarmInformationView) {
            FarmInformationView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.FarmEditView) {
            FarmEditView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.StartView) {
            StartView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.ProfileView) {
            ProfileView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }

        composable(Routes.ChangePasswordView) {
            ChangePasswordView(navController = navController)
            BackHandler {
                // Si no haces nada, el gesto queda bloqueado
            }
        }
    }
}
