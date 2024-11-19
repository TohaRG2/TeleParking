package ru.tohaman.mytestcomposeapplication.presentation.settings.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.tohaman.mytestcomposeapplication.util.PreferencesConstants.TAG


@Composable
fun TextEditRow(
    title: String,
    textValue: String = "",
    hint: String = "",
    onValueChange: (String) -> Unit,
    keyboardType:KeyboardType = KeyboardType.Companion.Ascii,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Companion.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = textValue,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Companion.End
            ),
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Companion.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = onDone
            ),
            maxLines = 3,
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextEditRowExample(){
    var inputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    TextEditRow(
        title = "Токен бота:",
        textValue = inputText,
        hint = "токен, полученный от BotFather",
        onValueChange = { inputText = it},
        onDone = {
            //TODO тут можно сделать проверку введенного текста
            Log.d(TAG, "TextEditRow: ${inputText}")
            focusManager.clearFocus()
        }
    )
}