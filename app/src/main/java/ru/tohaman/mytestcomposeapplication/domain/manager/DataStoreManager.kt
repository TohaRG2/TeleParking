package ru.tohaman.mytestcomposeapplication.domain.manager

import kotlinx.coroutines.flow.Flow
import ru.tohaman.mytestcomposeapplication.domain.model.SendingSettings

interface DataStoreManager {

    fun readSendingSettingsPref() : Flow<SendingSettings>
    suspend fun saveSendingSettingsPref(sendingSettings: SendingSettings)

}