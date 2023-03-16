package ru.gortea.petter.ui_kit.text_field

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class TextFieldState(
    val text: String = "",
    val isIncorrect: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None
)

fun TextFieldState.hideText(): TextFieldState {
    return copy(visualTransformation = PasswordVisualTransformation())
}
