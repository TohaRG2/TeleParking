package ru.tohaman.teleparking.presentation.home

data class HomeState(
    val botToken: String = "",
    val chatId: String = "",
    val parkingOutMessage: String = "",
    val parkingInMessage: String = "",
    val isParkingOutMessageSending: Boolean = false,
    val isParkingInMessageSending: Boolean = false,
    val error: String? = null,
)
