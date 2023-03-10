package ru.gortea.petter.auth.registration.registration_form.ui.state

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

internal data class TextFieldState(
    val text: String = "",
    val isIncorrect: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None
)

internal fun TextFieldState.hideText(): TextFieldState {
    return copy(visualTransformation = PasswordVisualTransformation())
}
