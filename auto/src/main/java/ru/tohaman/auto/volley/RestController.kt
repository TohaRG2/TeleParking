package ru.tohaman.auto.volley

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ru.tohaman.auto.BotToken
import ru.tohaman.auto.ChatId

class RestController(private val context: Context) {
    private val botToken = BotToken
    private val chatId = ChatId

    fun sendMessage(
        message: String = "testMessage",
        onSend: (response: String) -> Unit = {},
        onError: (error: String) -> Unit = {}
    ) {
        val url = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$message"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                onSend("$response")
            },
            { error ->
                onError("$error")
            }
        )
        request.setRetryPolicy(
            DefaultRetryPolicy(
                10,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        queue.add(request)
    }
}