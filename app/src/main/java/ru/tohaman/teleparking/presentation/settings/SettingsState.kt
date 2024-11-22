package ru.tohaman.teleparking.presentation.settings

data class SettingsState(
    val botToken : String = "",
    val chatId : String = "",
    val parkingOutMessage : String = "",
    val parkingInMessage : String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
)
