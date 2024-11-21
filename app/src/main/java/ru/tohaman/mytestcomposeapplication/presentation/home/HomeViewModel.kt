package ru.tohaman.mytestcomposeapplication.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tohaman.mytestcomposeapplication.domain.model.response.Response
import ru.tohaman.mytestcomposeapplication.domain.usecases.UseCases
import ru.tohaman.mytestcomposeapplication.util.PreferencesConstants.TAG
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {
    private var _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object NavToSettings : UiEvent()
    }

    init {
        Log.d(TAG, "Start HomeViewModel: ")
        useCases.preferencesManager().onEach { settings ->
            _state.value = _state.value.copy(
                botToken = settings.botToken,
                chatId = settings.chatId,
                parkingOutMessage = settings.parkingOutMessage,
                parkingInMessage = settings.parkingInMessage
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.PressSettingsButton -> pressSettingsButton()
            HomeUiEvent.ParkingOutPressed -> parkingOutPressed()
            HomeUiEvent.ParkingInPressed -> parkingInPressed()
            HomeUiEvent.ErrorResetPressed -> errorResetPressed()
        }
    }

    private fun pressSettingsButton() {
        _state.value = _state.value.copy(error = null)
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.NavToSettings)
        }
    }

    private fun parkingOutPressed() {
        _state.value = _state.value.copy(isParkingOutLoading = true, error = null)
        CoroutineScope(Dispatchers.IO).launch {
            sendMessage(_state.value.parkingOutMessage)
            _state.value = _state.value.copy(isParkingOutLoading = false)
        }
    }

    private fun parkingInPressed() {
        _state.value = _state.value.copy(isParkingInLoading = true, error = null)
        CoroutineScope(Dispatchers.IO).launch {
            sendMessage(_state.value.parkingInMessage)
            _state.value = _state.value.copy(isParkingInLoading = false)
        }
    }

    private suspend fun sendMessage(message: String) {
        Log.i(TAG, "sendMessage: $message ")
        val encodedToken = _state.value.botToken.replace(":", "%3A")
        val response = useCases.sendTelegramMessages(
            botToken = encodedToken,
            chatId = _state.value.chatId,
            message = message
        )
        when (response) {
            is Response.Success -> {
                Log.d(TAG, "$message sending Success ${response.result.ok}")
            }

            is Response.BadResponse -> {
                Log.d(TAG, "$message sending HttpException ${response.error.description}")
                _state.value = _state.value.copy(error = response.error.description)
            }

            is Response.Failure -> {
                Log.e(TAG, "$message sending ERROR ${response.error}")
                _state.value = _state.value.copy(error = response.error)
            }
        }
    }

    private fun errorResetPressed() {
        _state.value = _state.value.copy(error = null)
    }

}
