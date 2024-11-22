package ru.tohaman.teleparking.domain.usecases.telegram

import ru.tohaman.teleparking.domain.model.response.Response
import ru.tohaman.teleparking.domain.repo.TelegramRepository

class SendTelegramMessage(
    val repo: TelegramRepository,
) {

    suspend operator fun invoke(
        message: String,
        botToken: String = "",
        chatId: String = ""
    ): Response {
        return repo.sendMessageToChat(
            botToken = botToken,
            chatId = chatId,
            message = message
        )
    }

}