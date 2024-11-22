package ru.tohaman.teleparking.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.tohaman.teleparking.presentation.home.HomeScreen
import ru.tohaman.teleparking.presentation.settings.SettingsScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Route.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(route = Route.SettingsScreen.route) {
            SettingsScreen(navController)
        }
    }
}

