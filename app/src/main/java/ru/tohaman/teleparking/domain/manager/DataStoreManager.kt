package ru.tohaman.teleparking.domain.manager

import kotlinx.coroutines.flow.Flow
import ru.tohaman.teleparking.domain.model.SendingSettings

interface DataStoreManager {

    fun readSendingSettingsPref() : Flow<SendingSettings>
    suspend fun saveSendingSettingsPref(sendingSettings: SendingSettings)

}