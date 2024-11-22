package ru.tohaman.teleparking.domain.repo

import ru.tohaman.teleparking.domain.model.response.Response

interface TelegramRepository {

    suspend fun sendMessageToChat(
        botToken: String,
        message: String,
        chatId: String
    ): Response

}