package ru.tohaman.teleparking.android_auto.auto_screen

import android.util.Log
import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.GridTemplate
import androidx.car.app.model.ItemList
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.tohaman.teleparking.R
import ru.tohaman.teleparking.domain.model.response.Response
import ru.tohaman.teleparking.domain.usecases.UseCases
import ru.tohaman.teleparking.util.PreferencesConstants.TAG

/**
 * Объект [Screen] содержит шаблон и метаданные, которые мы хотим отображать на экране авто
 * Кроме этого, [Screen] имеет доступ к [ScreenManager], который можно использовать для
 * открытия других экранов
 */
class AutoScreen(
    carContext: CarContext,
    private val useCases: UseCases
) : Screen(carContext), DefaultLifecycleObserver {

    var state = AutoScreenState()

    init {
        lifecycle.addObserver(this)
        useCases.preferencesManager().onEach { settings ->
            state = state.copy(
                botToken = settings.botToken,
                chatId = settings.chatId,
                parkingInMessage = settings.parkingInMessage,
                parkingOutMessage = settings.parkingOutMessage,
            )
            Log.d(TAG, "Loaded: $state")
        }.launchIn(lifecycleScope)
    }

    // Создаем и возвращаем шаблон
    override fun onGetTemplate(): Template {
        Log.d(TAG, "onGetTemplate: start")

        val parkingInIcon = CarIcon.Builder(
            IconCompat.createWithResource(getCarContext(), R.drawable.ic_parking_in)
        ).build()
        val parkingOutIcon = CarIcon.Builder(
            IconCompat.createWithResource(getCarContext(), R.drawable.ic_parking_out)
        ).build()

        val gridItem1 = if (state.isParkingInMessageSending) {
            GridItem.Builder()
                .setTitle(carContext.getString(R.string.parking_in))
                .setLoading(true)
                .build()
        } else {
            GridItem.Builder()
                .setTitle(carContext.getString(R.string.parking_in))
                .setImage(CarIcon.Builder(parkingInIcon).build())
                .setOnClickListener(onClickIn)
                .build()
        }

        val gridItem2 = if (state.isParkingOutMessageSending) {
            GridItem.Builder()
                .setTitle(carContext.getString(R.string.parking_out))
                .setLoading(true)
                .build()
        } else {
            GridItem.Builder()
                .setTitle(carContext.getString(R.string.parking_out))
                .setImage(CarIcon.Builder(parkingOutIcon).build())
                .setOnClickListener(onClickOut)
                .build()
        }

        val gridItem3: GridItem = if (state.isParkingOutMessageSending) {
            GridItem.Builder()
                .setTitle(carContext.getString(R.string.parking_out))
                .setLoading(true)
                .build()
        } else {
            GridItem.Builder()
                .setTitle("Parking test")
                .setImage(CarIcon.Builder(parkingOutIcon).build())
                .setOnClickListener(onClickTest)
                .build()
        }

        val listBuilder = ItemList.Builder()
        listBuilder.addItem(gridItem1)
        listBuilder.addItem(gridItem2)
        listBuilder.addItem(gridItem3)

        return GridTemplate.Builder()
            .setTitle("OpenParking")
            .setHeaderAction(Action.APP_ICON)
            .setSingleList(listBuilder.build())
            .build()
    }

    val onClickIn = {
        state = state.copy(isParkingInMessageSending = true)
        invalidate()
        CoroutineScope(Dispatchers.IO).launch {
            val encodedToken = state.botToken.replace(":", "%3A")
            val response = useCases.sendTelegramMessages(
                botToken = encodedToken,
                chatId = state.chatId,
                message = state.parkingInMessage
            )
            when (response) {
                is Response.Success -> {
                    Log.d(TAG, "${state.parkingInMessage} sending Success ${response.result.ok}")
                    state = state.copy(toast = carContext.getString(R.string.parking_in_toast))
                }

                is Response.BadResponse -> {
                    Log.d(TAG, "${state.parkingInMessage} sending HttpException ${response.error.description}")
                    state = state.copy(toast = response.error.description)
                }

                is Response.Failure -> {
                    Log.e(TAG, "${state.parkingInMessage} sending ERROR ${response.error}")
                    state = state.copy(toast = response.error)
                }
            }
            showToast(state.toast)
            state = state.copy(isParkingInMessageSending = false)
        }
        invalidate()
    }


    val onClickOut = {
        state = state.copy(isParkingOutMessageSending = true)
        invalidate()
        CoroutineScope(Dispatchers.IO).launch {
            val encodedToken = state.botToken.replace(":", "%3A")
            val response = useCases.sendTelegramMessages(
                botToken = encodedToken,
                chatId = state.chatId,
                message = state.parkingOutMessage
            )
            when (response) {
                is Response.Success -> {
                    Log.d(TAG, "${state.parkingOutMessage} sending Success ${response.result.ok}")
                    state = state.copy(toast = carContext.getString(R.string.parking_out_toast))
                }

                is Response.BadResponse -> {
                    Log.d(TAG, "${state.parkingOutMessage} sending HttpException ${response.error.description}")
                    state = state.copy(toast = response.error.description)
                }

                is Response.Failure -> {
                    Log.e(TAG, "${state.parkingOutMessage} sending ERROR ${response.error}")
                    state = state.copy(toast = response.error)
                }
            }
            showToast(state.toast)
            state = state.copy(isParkingOutMessageSending = false)
        }
        invalidate()
    }

    val onClickTest = {
        state = state.copy(isParkingOutMessageSending = true)
        invalidate()
        CoroutineScope(Dispatchers.IO).launch {
            val encodedToken = state.botToken.replace(":", "%3A")
            val response = useCases.sendTelegramMessages(
                botToken = encodedToken,
                chatId = state.chatId,
                message = state.parkingOutMessage
            )
            when (response) {
                is Response.Success -> {
                    Log.d(TAG, "${state.parkingOutMessage} sending Success ${response.result.ok}")
                    state = state.copy(toast = carContext.getString(R.string.parking_out_toast))
                }

                is Response.BadResponse -> {
                    Log.d(TAG, "${state.parkingOutMessage} sending HttpException ${response.error.description}")
                    state = state.copy(toast = response.error.description)
                }

                is Response.Failure -> {
                    Log.e(TAG, "${state.parkingOutMessage} sending ERROR ${response.error}")
                    state = state.copy(toast = response.error)
                }
            }
            showToast(state.toast)
            state = state.copy(isParkingOutMessageSending = false)
        }
        invalidate()
    }

    fun showToast(message: String) {
        CarToast.makeText(carContext, message, CarToast.LENGTH_LONG).show()
    }

}

//const val OpenParkingOut = "open_parking_out"
//const val OpenParkingIn = "open_parking_in"
