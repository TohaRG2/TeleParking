package ru.tohaman.teleparking.domain.model.response

import ru.tohaman.teleparking.data.remote.dto.ErrorTelegramResponse
import ru.tohaman.teleparking.data.remote.dto.TelegramResponse

sealed class Response{
    data class Success(val result: TelegramResponse) : Response()
    data class BadResponse(val error: ErrorTelegramResponse) : Response()
    data class Failure(val error: String) : Response()
}
