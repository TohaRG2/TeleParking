package ru.tohaman.teleparking.presentation.settings

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tohaman.teleparking.data.mapper.toSendingSettings
import ru.tohaman.teleparking.data.mapper.toSettingsState
import ru.tohaman.teleparking.domain.usecases.UseCases
import ru.tohaman.teleparking.util.PreferencesConstants.TAG
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state : State<SettingsState> = _state

    lateinit var navController: NavHostController

    init {
        Log.d(TAG, "SettingsViewModel - Init:")
        viewModelScope.launch{
            useCases.preferencesManager().collect{ settings ->
                _state.value = settings.toSettingsState()
                Log.d(TAG, "SettingsViewModel - Init2: ${_state.value}")
            }
        }

    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            SettingsUiEvent.Back -> back()
            is SettingsUiEvent.ChangeBotTokenFocus -> changeBotTokenFocus(event)
            is SettingsUiEvent.ChangeChatIdFocus -> changeChatIdFocus(event)
            is SettingsUiEvent.ChangeParkingInFocus -> changeParkingInFocus(event)
            is SettingsUiEvent.ChangeParkingOutFocus -> changeParkingOutFocus(event)
            is SettingsUiEvent.EnteredBotToken -> enteredBotToken(event)
            is SettingsUiEvent.EnteredChatId -> enteredChatId(event)
            is SettingsUiEvent.EnteredParkingIn -> enteredParkingIn(event)
            is SettingsUiEvent.EnteredParkingOut -> enteredParkingOut(event)
        }
    }

    fun back() {
        navController.popBackStack()
    }

    fun changeBotTokenFocus(event: SettingsUiEvent.ChangeBotTokenFocus) {
        event.focusManager.clearFocus()
        viewModelScope.launch {
            useCases.preferencesManager.save(_state.value.toSendingSettings())
        }
    }

    fun changeChatIdFocus(event: SettingsUiEvent.ChangeChatIdFocus) {
        event.focusManager.clearFocus()
        viewModelScope.launch {
            useCases.preferencesManager.save(_state.value.toSendingSettings())
        }
    }

    fun changeParkingOutFocus(event: SettingsUiEvent.ChangeParkingOutFocus) {
        event.focusManager.clearFocus()
        viewModelScope.launch{
            useCases.preferencesManager.save(_state.value.toSendingSettings())
        }
    }

    fun changeParkingInFocus(event: SettingsUiEvent.ChangeParkingInFocus) {
        event.focusManager.clearFocus()
        viewModelScope.launch {
            useCases.preferencesManager.save(_state.value.toSendingSettings())
        }
    }

    fun enteredBotToken(event: SettingsUiEvent.EnteredBotToken) {
        _state.value = _state.value.copy(botToken = event.value)
    }

    fun enteredChatId(event: SettingsUiEvent.EnteredChatId) {
        _state.value = _state.value.copy(chatId = event.value)
    }

    fun enteredParkingOut(event: SettingsUiEvent.EnteredParkingOut) {
        _state.value = _state.value.copy(parkingOutMessage = event.value)
    }

    fun enteredParkingIn(event: SettingsUiEvent.EnteredParkingIn) {
        _state.value = _state.value.copy(parkingInMessage = event.value)
    }

}