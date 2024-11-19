package ru.tohaman.mytestcomposeapplication.domain.model.response.data

data class Result(
    val chat: Chat,
    val date: Int,
    val message_id: Int,
    val sender_chat: SenderChat,
    val text: String
)
