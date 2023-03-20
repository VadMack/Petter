package ru.gortea.petter.auth.common

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal data class FieldState(
    val text: String = "",
    val isValid: Boolean = true,
    val isVisible: Boolean = true
)

internal fun FieldState.toTextFieldState(): TextFieldState {
    val transform = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    return TextFieldState(
        text = text,
        isIncorrect = !isValid,
        visualTransformation = transform
    )
}

internal fun FieldState.isBlank(): Boolean = text.isBlank()
internal fun FieldState.isNotBlank(): Boolean = text.isNotBlank()

internal fun FieldState.text(text: String): FieldState {
    return copy(text = text, isValid = true)
}

internal fun FieldState.hide(): FieldState {
    return copy(isVisible = false)
}

internal fun FieldState.valid(): FieldState {
    return copy(isValid = true)
}

internal fun FieldState.invalid(): FieldState {
    return copy(isValid = false)
}
