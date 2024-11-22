package ru.tohaman.teleparking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Создаем свой класс Application(), навешиваем на него аннотацию HiltAndroidApp
 * и прописываем в манифест,
 * android:name=".TeleParkingApplication"
 * что теперь запускаемся на этом классе, а не анонимном Application()
 */
@HiltAndroidApp
class TeleParkingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}