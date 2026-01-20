package com.rpc.mayaandroidexam.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rpc.mayaandroidexam.ui.history.TransactionHistoryScreen
import com.rpc.mayaandroidexam.ui.home.HomeScreen
import com.rpc.mayaandroidexam.ui.login.LoginScreen
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.send.SendMoneyScreen

@Composable
fun App() {
    val navController = rememberNavController()
    println("navDebug called App")
    NavHost(navController = navController, Routes.Auth.Login) {
        composable(Routes.Auth.Login) {
            LoginScreen { route ->
                handleNavigation(navController, route)
            }
        }
        navigation(
            startDestination = Routes.Main.Send,
            route = Routes.Main.Route
        ) {
            composable(Routes.Main.Home) {
                HomeScreen { route ->
                    handleNavigation(navController, route)
                }
            }
            composable(Routes.Main.Send) {
                SendMoneyScreen { route ->
                    handleNavigation(navController, route)
                }
            }
            composable(Routes.Main.History) {
                TransactionHistoryScreen()
            }
        }
    }
}

private fun handleNavigation(navController: NavController, route: String) {
    println("OkHttp handleNavigation=$route")
    when (route) {
        Routes.Main.Route -> {
            navController.popBackStack()
            navController.navigate(route)
        }
        Routes.Main.Home -> {
            navController.popBackStack()
            navController.navigate(route)
        }
        Routes.Auth.Login -> {
            navController.popBackStack()
            navController.navigate(route)
        }
        Routes.Main.Send -> {
            navController.popBackStack()
            navController.navigate(route)
        }
        Routes.Main.History -> {
            navController.popBackStack()
            navController.navigate(route)
        }
    }
}