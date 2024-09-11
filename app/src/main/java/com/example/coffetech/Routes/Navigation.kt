// Navigation.kt

package com.example.coffetech.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coffetech.Routes.Routes
import com.example.coffetech.Routes.Routes.FarmView
import com.example.coffetech.view.Auth.LoginView
import com.example.coffetech.view.Auth.RegisterView
import com.example.coffetech.view.Auth.ForgotPasswordView
import com.example.coffetech.view.Auth.ConfirmTokenForgotPasswordView
import com.example.coffetech.view.Auth.VerifyAccountView
import com.example.coffetech.view.Auth.AlertSendView
import com.example.coffetech.view.Auth.NewPasswordView
import com.example.coffetech.view.Auth.StartView
import com.example.coffetech.view.farm.FarmView

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LoginView) {

        composable(Routes.LoginView) {
            LoginView(navController = navController)
        }

        composable(Routes.RegisterView) {
            RegisterView(navController = navController)
        }

        composable(Routes.ForgotPasswordView) {
            ForgotPasswordView(navController = navController)
        }

        composable(Routes.ConfirmTokenForgotPasswordView) {
            ConfirmTokenForgotPasswordView(navController = navController)
        }

        composable(Routes.VerifyAccountView) {
            VerifyAccountView(navController = navController)
        }

        composable(Routes.AlertSendView) {
            AlertSendView(navController = navController)
        }

        composable(
            route = "${Routes.NewPasswordView}/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recuperar el token del backStackEntry
            val token = backStackEntry.arguments?.getString("token") ?: ""

            // Llamar a la vista NewPasswordView con el token obtenido
            NewPasswordView(navController = navController, token = token)
        }

        composable(Routes.FarmView) {
            FarmView(navController = navController)
        }
        composable(Routes.StartView) {
            StartView(navController = navController)
        }

    }
}
