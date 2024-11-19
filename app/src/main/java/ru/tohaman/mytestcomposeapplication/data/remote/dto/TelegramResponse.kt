package ru.tohaman.mytestcomposeapplication.data.remote.dto

import ru.tohaman.mytestcomposeapplication.domain.model.response.data.Result

data class TelegramResponse(
    val ok: Boolean,
    val result: Result,
)