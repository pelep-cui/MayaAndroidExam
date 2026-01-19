package com.rpc.mayaandroidexam.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rpc.mayaandroidexam.ui.navigation.Routes
import com.rpc.mayaandroidexam.ui.splash.SplashScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, Routes.Auth.Splash) {
        navigation(
            startDestination = Routes.Auth.Splash,
            route = Routes.Auth.Route,
        ) {
            composable(Routes.Auth.Splash) {
                SplashScreen()
            }
        }
    }
}