package ru.gortea.petter.ui_kit.text_field

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class TextFieldState(
    val text: String = "",
    val keyboardType: KeyboardType = KeyboardType.Text,
    val isIncorrect: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None
) {
    internal var textRes: Int? = null
}

fun TextFieldState.textRes(textRes: Int): TextFieldState {
    return copy().apply {
        this.textRes = textRes
    }
}

fun TextFieldState.isNumber(): Boolean {
    return keyboardType == KeyboardType.Number
}

fun TextFieldState.email(): TextFieldState {
    return copy(keyboardType = KeyboardType.Email)
}

fun TextFieldState.number(): TextFieldState {
    return copy(keyboardType = KeyboardType.Number)
}

fun TextFieldState.hideText(): TextFieldState {
    return copy(visualTransformation = PasswordVisualTransformation())
}
