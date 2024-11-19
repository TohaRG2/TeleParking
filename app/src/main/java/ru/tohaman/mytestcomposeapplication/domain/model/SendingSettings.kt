package ru.tohaman.mytestcomposeapplication.domain.model

data class SendingSettings(
    val botToken: String = "",
    val chatId: String = "",
    val parkingOutMessage: String = "",
    val parkingInMessage: String = "",
)
