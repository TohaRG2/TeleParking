package ru.tohaman.teleparking.android_auto.auto_screen

import android.os.Handler
import android.os.Looper
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tohaman.teleparking.android_auto.OpenParkingIn
import ru.tohaman.teleparking.android_auto.OpenParkingOut
import ru.tohaman.teleparking.android_auto.OpenParkingTest
import ru.tohaman.teleparking.android_auto.volley.RestController
import ru.tohaman.teleparking.domain.usecases.UseCases
import ru.tohaman.teleparking.R
import ru.tohaman.teleparking.util.PreferencesConstants.TAG

/**
 * Объект [Screen] содержит шаблон и метаданные, которые мы хотим отображать на экране авто
 * Кроме этого, [Screen] имеет доступ к [ScreenManager], который можно использовать для
 * открытия других экранов
 */
class AutoScreen (
    carContext: CarContext,
    private val useCases: UseCases
) : Screen(carContext), DefaultLifecycleObserver {

    private val mHandler = Handler(Looper.getMainLooper())
    private var isParkingInLoading: Boolean = false
    private var isParkingOutLoading: Boolean = false
    val restController = RestController(carContext)
    val viewModelStoreOwner = getViewModelStoreOwner()
    var botToken: String = ""
    var chatId: String = ""

    init {
        lifecycle.addObserver(this)
        isParkingInLoading = false
        isParkingOutLoading = false
        useCases.preferencesManager().onEach { settings ->
                botToken = settings.botToken
                chatId = settings.chatId
                Log.d(TAG, "Loaded: $botToken $chatId")
        }.launchIn(lifecycleScope)
    }

    // Создаем и возвращаем шаблон
    override fun onGetTemplate(): Template {
        Log.d(TAG, "onGetTemplate: start")
        val listBuilder = ItemList.Builder()
        val parkingInIcon = CarIcon.Builder(
            IconCompat.createWithResource(getCarContext(),
            R.drawable.ic_parking_in
        )).build()
        val parkingOutIcon = CarIcon.Builder(
            IconCompat.createWithResource(getCarContext(),
            R.drawable.ic_parking_out
        )).build()

        val gridItem1 = GridItem.Builder()
            .setTitle("Parking In")
            .setImage(CarIcon.Builder(parkingInIcon).build())
            // Handle user interactions
            .setOnClickListener {
                restController.sendMessage(OpenParkingIn)
                CarToast.makeText(carContext, "Дверь на въезд открыта (IN)", CarToast.LENGTH_SHORT).show()
            }
            .build()

        val gridItem2 = GridItem.Builder()
            .setTitle("Parking Out")
            .setImage(CarIcon.Builder(parkingOutIcon).build())
            .setOnClickListener {
                restController.sendMessage(OpenParkingOut)
                CarToast.makeText(carContext, "Дверь на выезд открыта (OUT)", CarToast.LENGTH_SHORT).show()
            }
            .build()

        val gridItem3: GridItem = if (isParkingOutLoading) {
            GridItem.Builder()
                .setTitle("Parking test")
                .setLoading(true)
                .build()
        } else {
            GridItem.Builder()
                .setTitle("Parking test")
                .setImage(CarIcon.Builder(parkingOutIcon).build())
                .setOnClickListener { //this.triggerFourthItemLoading()
                    isParkingOutLoading = true
                    invalidate()
                    restController.sendMessage(
                        message = OpenParkingTest,
                        onSend = { response ->
                            Log.d(TAG, "onGetTemplate: $response")
                            CarToast.makeText(carContext, "Дверь открывается", CarToast.LENGTH_SHORT).show()
                            isParkingOutLoading = false
                            invalidate()
                        },
                        onError = { error ->
                            Log.e(TAG, "onGetTemplate: $error")
                            CarToast.makeText(carContext, "Не удалось открыть дверь", CarToast.LENGTH_SHORT).show()
                            isParkingOutLoading = false
                            invalidate()
                        }
                    )
                }
                .build()
        }



        listBuilder.addItem(gridItem1)
        listBuilder.addItem(gridItem2)
        listBuilder.addItem(gridItem3)

        return GridTemplate.Builder()
            .setTitle("OpenParking")
            .setHeaderAction(Action.APP_ICON)
            .setSingleList(listBuilder.build())
            .build()
    }

    private fun triggerFourthItemLoading() {
        mHandler.post(
            Runnable {
                isParkingInLoading = true
                invalidate()
                mHandler.postDelayed(
                    Runnable {
                        isParkingInLoading = false
                        invalidate()
                    },
                    LOADING_TIME_MILLIS.toLong()
                )
            })
    }

    companion object {
        private const val MAX_GRID_ITEMS = 100
        private const val LOADING_TIME_MILLIS = 2000
    }

}

