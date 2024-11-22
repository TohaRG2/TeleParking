package ru.tohaman.teleparking.android_auto.auto_screen

data class AutoScreenState (
    val botToken : String = "",
    val chatId : String = "",
    val parkingOutMessage : String = "",
    val parkingInMessage : String = "",
    val isParkingOutMessageSending: Boolean = false,
    val isParkingInMessageSending: Boolean = false,
    val toast: String = "",
)