package ru.tohaman.mytestcomposeapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module


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