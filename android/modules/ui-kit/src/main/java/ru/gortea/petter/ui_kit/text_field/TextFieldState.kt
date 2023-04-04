package ru.gortea.petter.ui_kit.text_field

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.gortea.petter.ui_kit.TextModel

data class TextFieldState(
    val text: TextModel = TextModel.StringText(""),
    val keyboardType: KeyboardType = KeyboardType.Text,
    val isIncorrect: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None
) {
    constructor(
        text: String,
        keyboardType: KeyboardType = KeyboardType.Text,
        isIncorrect: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) : this(TextModel.StringText(text), keyboardType, isIncorrect, visualTransformation)

    constructor(
        @StringRes textRes: Int,
        keyboardType: KeyboardType = KeyboardType.Text,
        isIncorrect: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None
    ) : this(TextModel.ResText(textRes), keyboardType, isIncorrect, visualTransformation)
}

fun TextFieldState.text(@StringRes textRes: Int): TextFieldState {
    return copy(text = TextModel.ResText(textRes))
}

fun TextFieldState.text(text: String): TextFieldState {
    return copy(text = TextModel.StringText(text))
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

fun TextFieldState.isEmpty(): Boolean {
    return text.getStringText().isEmpty()
}

fun TextFieldState.isNotEmpty(): Boolean {
    return text.getStringText().isNotEmpty()
}
