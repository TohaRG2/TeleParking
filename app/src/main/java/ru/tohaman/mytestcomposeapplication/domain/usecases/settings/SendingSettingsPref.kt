package ru.tohaman.mytestcomposeapplication.domain.usecases.settings

import kotlinx.coroutines.flow.Flow
import ru.tohaman.mytestcomposeapplication.domain.manager.DataStoreManager
import ru.tohaman.mytestcomposeapplication.domain.model.SendingSettings

class SendingSettingsPref(private val prefManager: DataStoreManager) {


    operator fun invoke(): Flow<SendingSettings> {
        return prefManager.readSendingSettingsPref()
    }

    suspend fun save(newValue: SendingSettings) {
        prefManager.saveSendingSettingsPref(newValue)
    }

}