package ru.tohaman.mytestcomposeapplication.presentation.navgraph

sealed class Route(val route: String) {
    object HomeScreen : Route(route = "homeScreen")
    object SettingsScreen : Route(route = "settingsScreen")
}