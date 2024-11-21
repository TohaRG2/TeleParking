package ru.tohaman.mytestcomposeapplication.android_auto.auto_screen

import android.content.Intent
import android.content.res.Configuration
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import ru.tohaman.mytestcomposeapplication.domain.usecases.UseCases


//Сессия - это объект, который возвращает первый экран, который будет отображаться при открытии
// приложения на экране автомобиля
class AutoSession(
    val useCases: UseCases
) : Session(), DefaultLifecycleObserver {

    override fun onCreateScreen(intent: Intent): Screen {
        return AutoScreen(carContext, useCases)
    }

    //Сессия так же получает колбэки при изменениях жизненного цикла приложения
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

    override fun onCarConfigurationChanged(newConfiguration: Configuration) {
        super.onCarConfigurationChanged(newConfiguration)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

}