package ru.tohaman.teleparking.domain.usecases.preferences

import kotlinx.coroutines.flow.Flow
import ru.tohaman.teleparking.domain.manager.DataStoreManager
import ru.tohaman.teleparking.domain.model.SendingSettings

class PreferencesManager(private val prefManager: DataStoreManager) {


    operator fun invoke(): Flow<SendingSettings> {
        return prefManager.readSendingSettingsPref()
    }

    suspend fun save(newValue: SendingSettings) {
        prefManager.saveSendingSettingsPref(newValue)
    }

}