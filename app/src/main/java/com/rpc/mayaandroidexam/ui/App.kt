package com.rpc.mayaandroidexam.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rpc.mayaandroidexam.ui.home.HomeScreen
import com.rpc.mayaandroidexam.ui.login.LoginScreen
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.send.SendMoneyScreen

@Composable
fun App() {
    val navController = rememberNavController()
    println("navDebug called App")
    NavHost(navController = navController, Routes.Main.Route) {
        navigation(
            startDestination = Routes.Auth.Login,
            route = Routes.Auth.Route,
        ) {
            composable(Routes.Auth.Login) {
                LoginScreen()
            }
        }
        navigation(
            startDestination = Routes.Main.Send,
            route = Routes.Main.Route
        ) {
            composable(Routes.Main.Home) {
                HomeScreen()
            }
            composable(Routes.Main.Send) {
                SendMoneyScreen()
            }
        }
    }
}