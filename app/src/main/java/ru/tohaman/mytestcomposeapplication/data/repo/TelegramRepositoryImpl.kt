package ru.tohaman.mytestcomposeapplication.data.repo

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import ru.tohaman.mytestcomposeapplication.data.remote.TelegramApi
import ru.tohaman.mytestcomposeapplication.data.remote.dto.ErrorTelegramResponse
import ru.tohaman.mytestcomposeapplication.domain.model.response.Response
import ru.tohaman.mytestcomposeapplication.domain.repo.TelegramRepository
import ru.tohaman.mytestcomposeapplication.util.PreferencesConstants.TAG
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TelegramRepositoryImpl(
    private val telegramApi: TelegramApi,
) : TelegramRepository {

    override suspend fun sendMessageToChat(
        botToken: String,
        message: String,
        chatId: String
    ): Response {
        try {
            Log.i(TAG, "sendMessageToChat: botToken = $botToken, chatId = $chatId, message = $message")
            val response = telegramApi.sendMessage(
                botToken = botToken,
                chatId = chatId,
                message = message
            )
            return Response.Success(response)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException -> {
                    Log.e("sendMessageToChat", "ERROR: Can't send message $message to chat $chatId")
                    val errorResponse = ErrorTelegramResponse(errorCode = 404, description = "${e.message}")
                    return Response.BadResponse(error = errorResponse)
                }
                is HttpException -> {
                    val err = "${e.response()?.errorBody()?.string()}"
                    Log.e("sendMessageToChat", "ERROR: Can't send message $message to chat $chatId, response - $err")
                    val errorResponse = GsonBuilder().create().fromJson(err, ErrorTelegramResponse::class.java)
                    return Response.BadResponse(error = errorResponse)
                }
                is SocketTimeoutException -> {
                    Log.e("sendMessageToChat", "ERROR: Can't send message $message to chat $chatId, error - ${e.message}")
                    val errorResponse = ErrorTelegramResponse(errorCode = 504, description = "${e.message}")
                    return Response.BadResponse(error = errorResponse)
                }
                else -> {
                    Log.e("sendMessageToChat", "ERROR: ${e.stackTrace}")
                    return Response.Failure(error = e.message ?: "Unknown error")
                }
            }
        }
    }
}
