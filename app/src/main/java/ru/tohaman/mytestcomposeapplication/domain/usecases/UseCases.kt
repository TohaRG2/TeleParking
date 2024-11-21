package ru.tohaman.mytestcomposeapplication.domain.usecases

import ru.tohaman.mytestcomposeapplication.domain.usecases.telegram.SendTelegramMessage
import ru.tohaman.mytestcomposeapplication.domain.usecases.preferences.PreferencesManager

data class UseCases(
    val sendTelegramMessages: SendTelegramMessage,
    val preferencesManager: PreferencesManager,
)
