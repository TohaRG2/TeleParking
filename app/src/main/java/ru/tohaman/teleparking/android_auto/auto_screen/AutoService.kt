package ru.tohaman.teleparking.android_auto.auto_screen


import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator
import dagger.hilt.android.AndroidEntryPoint
import ru.tohaman.teleparking.domain.usecases.UseCases
import javax.inject.Inject

/** Этот класс является основным мостом между приложением и хостом (MHU)
 * [CarAppService] is the main interface between the app and the car host. For more
 * details, see the [Android for
* Cars Library developer guide](https://developer.android.com/training/cars/navigation).
 */
@AndroidEntryPoint
class AutoService : CarAppService() {
    @Inject
    lateinit var useCases: UseCases

    override fun onCreateSession(): Session {
        return AutoSession(useCases)
    }

    // В этом методе проверяем, можно ли доверять хосту, который привязывается к нашему сервису
    override fun createHostValidator(): HostValidator {
//        return HostValidator.Builder(applicationContext)
//            .addAllowedHosts(R.array.hosts_allowlist_sample)
//            .build()
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

}