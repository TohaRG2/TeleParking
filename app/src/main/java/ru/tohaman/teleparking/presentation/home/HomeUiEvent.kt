package ru.tohaman.teleparking.presentation.home

sealed class HomeUiEvent {
    object PressSettingsButton: HomeUiEvent()
    object ParkingInPressed: HomeUiEvent()
    object ParkingOutPressed: HomeUiEvent()
    object ErrorResetPressed: HomeUiEvent()
}