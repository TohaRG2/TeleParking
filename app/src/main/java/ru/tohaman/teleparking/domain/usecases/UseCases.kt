package ru.tohaman.teleparking.domain.usecases

import ru.tohaman.teleparking.domain.usecases.telegram.SendTelegramMessage
import ru.tohaman.teleparking.domain.usecases.preferences.PreferencesManager

data class UseCases(
    val sendTelegramMessages: SendTelegramMessage,
    val preferencesManager: PreferencesManager,
)
