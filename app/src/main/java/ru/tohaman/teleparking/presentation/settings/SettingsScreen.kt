package ru.tohaman.teleparking.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.tohaman.teleparking.R
import ru.tohaman.teleparking.presentation.settings.components.TextEditRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    viewModel.navController = navController
    val event: (SettingsUiEvent) -> Unit = viewModel::onEvent

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            event(SettingsUiEvent.Back)
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Body(viewModel = viewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Body(viewModel: SettingsViewModel){
    val state = viewModel.state.value
    val event: (SettingsUiEvent) -> Unit = viewModel::onEvent
    val focusManager = LocalFocusManager.current

    TextEditRow(
        title = stringResource(R.string.bot_token),
        textValue = state.botToken,
        hint = stringResource(R.string.bot_token_hint),
        onValueChange = {newValue -> event(SettingsUiEvent.EnteredBotToken(newValue)) },
        onDone = { event(SettingsUiEvent.ChangeBotTokenFocus(focusManager)) }
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextEditRow(
        title = stringResource(R.string.chat_id),
        textValue = state.chatId,
        hint = stringResource(R.string.chat_id_hint),
        keyboardType = KeyboardType.Companion.Number,
        onValueChange = {newValue -> event(SettingsUiEvent.EnteredChatId(newValue)) },
        onDone = { event(SettingsUiEvent.ChangeChatIdFocus(focusManager)) }
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextEditRow(
        title = stringResource(R.string.parking_out_text),
        textValue = state.parkingOutMessage,
        hint = stringResource(R.string.parking_out_hint),
        onValueChange = {newValue -> event(SettingsUiEvent.EnteredParkingOut(newValue)) },
        onDone = { event(SettingsUiEvent.ChangeParkingOutFocus(focusManager)) }
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextEditRow(
        title = stringResource(R.string.parking_in_text),
        textValue = state.parkingInMessage,
        hint = stringResource(R.string.parking_in_hint),
        onValueChange = {newValue -> event(SettingsUiEvent.EnteredParkingIn(newValue)) },
        onDone = { event(SettingsUiEvent.ChangeParkingInFocus(focusManager)) }
    )
}