package ru.tohaman.mytestcomposeapplication.data.mapper

import ru.tohaman.mytestcomposeapplication.domain.model.SendingSettings
import ru.tohaman.mytestcomposeapplication.presentation.settings.SettingsState

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