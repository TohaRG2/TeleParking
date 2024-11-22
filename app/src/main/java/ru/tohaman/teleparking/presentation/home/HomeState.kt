package ru.tohaman.teleparking.presentation.home

data class HomeState(
    val botToken: String = "",
    val chatId: String = "",
    val parkingOutMessage: String = "",
    val parkingInMessage: String = "",
    val isParkingOutLoading: Boolean = false,
    val isParkingInLoading: Boolean = false,
    val error: String? = null,
)
