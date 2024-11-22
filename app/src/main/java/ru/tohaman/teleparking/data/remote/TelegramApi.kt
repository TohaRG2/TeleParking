package ru.tohaman.teleparking.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.tohaman.teleparking.data.remote.dto.TelegramResponse

interface TelegramApi {

    @GET("bot{botToken}/sendMessage")
    suspend fun sendMessage(
        @Path(value = "botToken", encoded = true) botToken: String,
        @Query("chat_id") chatId: String,
        @Query("text") message: String
    ): TelegramResponse

}
