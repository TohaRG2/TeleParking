package ru.tohaman.mytestcomposeapplication.domain.repo

import ru.tohaman.mytestcomposeapplication.domain.model.response.Response

interface TelegramRepository {

    suspend fun sendMessageToChat(
        botToken: String,
        message: String,
        chatId: String
    ): Response

}