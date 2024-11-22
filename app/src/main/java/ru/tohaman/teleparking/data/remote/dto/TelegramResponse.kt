package ru.tohaman.teleparking.data.remote.dto

import ru.tohaman.teleparking.domain.model.response.data.Result

data class TelegramResponse(
    val ok: Boolean,
    val result: Result,
)