package ru.tohaman.teleparking.data.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tohaman.teleparking.data.manager.DataStoreManagerImpl
import ru.tohaman.teleparking.data.remote.TelegramApi
import ru.tohaman.teleparking.data.repo.TelegramRepositoryImpl
import ru.tohaman.teleparking.domain.manager.DataStoreManager
import ru.tohaman.teleparking.domain.repo.TelegramRepository
import ru.tohaman.teleparking.domain.usecases.UseCases
import ru.tohaman.teleparking.domain.usecases.telegram.SendTelegramMessage
import ru.tohaman.teleparking.domain.usecases.preferences.PreferencesManager
import ru.tohaman.teleparking.util.TelegramConstants.BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePreferencesManager(application: Application): DataStoreManager =
        DataStoreManagerImpl(application)

    @Singleton
    @Provides
    fun provideInterceptorClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
//            .connectTimeout(30, TimeUnit.MILLISECONDS)
            .build()
        return client
    }

    @Singleton
    @Provides
    fun provideTelegramApi(client: OkHttpClient): TelegramApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TelegramApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTelegramRepository(telegramAPi: TelegramApi): TelegramRepository =
        TelegramRepositoryImpl(telegramAPi)

    @Singleton
    @Provides
    fun provideHomeUseCases(
        telegramRepository: TelegramRepository,
        dataStoreManager: DataStoreManager
    ): UseCases {
        return UseCases(
            sendTelegramMessages = SendTelegramMessage(repo = telegramRepository),
            preferencesManager = PreferencesManager(dataStoreManager)
        )
    }


}