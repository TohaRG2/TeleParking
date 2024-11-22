package ru.tohaman.teleparking.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ErrorTelegramResponse (
    val ok: Boolean = false,
    @SerializedName("error_code")
    val errorCode: Int = 410,
    val description: String = "Unknown error",
    )