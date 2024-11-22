package ru.tohaman.teleparking.presentation.settings

import androidx.compose.ui.focus.FocusManager

sealed class SettingsUiEvent {
    data class EnteredBotToken(val value: String): SettingsUiEvent()
    data class ChangeBotTokenFocus(val focusManager: FocusManager): SettingsUiEvent()
    data class EnteredChatId(val value: String): SettingsUiEvent()
    data class ChangeChatIdFocus(val focusManager: FocusManager): SettingsUiEvent()
    data class EnteredParkingOut(val value: String): SettingsUiEvent()
    data class ChangeParkingOutFocus(val focusManager: FocusManager): SettingsUiEvent()
    data class EnteredParkingIn(val value: String): SettingsUiEvent()
    data class ChangeParkingInFocus(val focusManager: FocusManager): SettingsUiEvent()
    object Back: SettingsUiEvent()
}