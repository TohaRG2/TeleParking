package ru.tohaman.auto.auto_screen

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

/** Этот класс является основным мостом между приложением и хостом (MHU)
 * [CarAppService] is the main interface between the app and the car host. For more
 * details, see the [Android for
* Cars Library developer guide](https://developer.android.com/training/cars/navigation).
 */
class MainService : CarAppService() {
    override fun onCreateSession(): Session {
        return MainSession()
    }

    // В этом методе проверяем, можно ли доверять хосту, который привязывается к нашему сервису
    // TODO подумать можно ли разрешить все хосты (для проверки на реальном устройстве, а не на эмуляторе)
    // попробовать вернуть HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    override fun createHostValidator(): HostValidator {
//        return HostValidator.Builder(applicationContext)
//            .addAllowedHosts(R.array.hosts_allowlist_sample)
//            .build()
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

}