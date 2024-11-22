package ru.tohaman.teleparking.data.mapper

import ru.tohaman.teleparking.domain.model.SendingSettings
import ru.tohaman.teleparking.presentation.settings.SettingsState

fun SettingsState.toSendingSettings(): SendingSettings{
    return SendingSettings(
        botToken = botToken,
        chatId = chatId,
        parkingOutMessage = parkingOutMessage,
        parkingInMessage = parkingInMessage,
    )
}

fun SendingSettings.toSettingsState(): SettingsState{
    return SettingsState(
        botToken = botToken,
        chatId = chatId,
        parkingOutMessage = parkingOutMessage,
        parkingInMessage = parkingInMessage,
        isLoading = true,
        error = null
    )
}