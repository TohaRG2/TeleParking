package ru.tohaman.teleparking.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import ru.tohaman.teleparking.R
import ru.tohaman.teleparking.presentation.home.components.ActionButton
import ru.tohaman.teleparking.presentation.navgraph.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val event: (HomeUiEvent) -> Unit = viewModel::onEvent

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                HomeViewModel.UiEvent.NavToSettings -> {
                    navController.navigate(Route.SettingsScreen.route)
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home_screen_title),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(onClick = {
                        event(HomeUiEvent.PressSettingsButton)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.settings)
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
            if (viewModel.state.value.isParkingOutMessageSending) {
                Box(
                    modifier = Modifier.weight(0.4f).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ActionButton(
                    onClick = { event(HomeUiEvent.ParkingOutPressed) },
                    icon = R.drawable.ic_parking_out,
                    text = stringResource(id = R.string.parking_out),
                    modifier = Modifier.weight(0.4f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (viewModel.state.value.isParkingInMessageSending) {
                Box(
                    modifier = Modifier.weight(0.4f).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ActionButton(
                    onClick = { event(HomeUiEvent.ParkingInPressed) },
                    icon = R.drawable.ic_parking_in,
                    text = stringResource(id = R.string.parking_in),
                    modifier = Modifier.weight(0.4f)
                )
            }
            if (viewModel.state.value.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.weight(0.3f).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = viewModel.state.value.error.toString(),
                        fontSize = 18.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { event(HomeUiEvent.ErrorResetPressed) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}
