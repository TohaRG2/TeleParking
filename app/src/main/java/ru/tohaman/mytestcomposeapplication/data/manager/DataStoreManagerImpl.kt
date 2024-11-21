package ru.tohaman.mytestcomposeapplication.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tohaman.mytestcomposeapplication.domain.manager.DataStoreManager
import ru.tohaman.mytestcomposeapplication.domain.model.SendingSettings
import ru.tohaman.mytestcomposeapplication.util.PreferencesConstants


class DataStoreManagerImpl(private val context: Context): DataStoreManager {

    override fun readSendingSettingsPref() : Flow<SendingSettings> =
        context.dataStore.data.map { preferences ->
            return@map SendingSettings(
                botToken = preferences[PreferencesKeys.BOT_TOKEN] ?: "",
                chatId = preferences[PreferencesKeys.CHAT_ID] ?: "",
                parkingOutMessage = preferences[PreferencesKeys.PARKING_OUT] ?: "",
                parkingInMessage = preferences[PreferencesKeys.PARKING_IN] ?: "",
            )
        }


    override suspend fun saveSendingSettingsPref(sendingSettings: SendingSettings) {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.BOT_TOKEN] = sendingSettings.botToken
            pref[PreferencesKeys.CHAT_ID] = sendingSettings.chatId
            pref[PreferencesKeys.PARKING_OUT] = sendingSettings.parkingOutMessage
            pref[PreferencesKeys.PARKING_IN] = sendingSettings.parkingInMessage
        }
    }


}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PreferencesConstants.USER_SETTINGS)

private object PreferencesKeys{
    val BOT_TOKEN = stringPreferencesKey(name = PreferencesConstants.BOT_TOKEN)
    val CHAT_ID = stringPreferencesKey(name = PreferencesConstants.CHAT_ID)
    val PARKING_IN = stringPreferencesKey(name = PreferencesConstants.PARKING_IN)
    val PARKING_OUT = stringPreferencesKey(name = PreferencesConstants.PARKING_OUT)
}