package ru.gortea.petter.arch.android.util

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.gortea.petter.ui_kit.text_field.TextFieldState

data class FieldState(
    val text: String = "",
    val isValid: Boolean = true,
    val isVisible: Boolean = true
)

fun FieldState.toTextFieldState(): TextFieldState {
    val transform = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    return TextFieldState(
        text = text,
        isIncorrect = !isValid,
        visualTransformation = transform
    )
}

fun FieldState.isBlank(): Boolean = text.isBlank()
fun FieldState.isNotBlank(): Boolean = text.isNotBlank()

fun FieldState.text(text: String): FieldState {
    return copy(text = text, isValid = true)
}

fun FieldState.hide(): FieldState {
    return copy(isVisible = false)
}

fun FieldState.valid(): FieldState {
    return copy(isValid = true)
}

fun FieldState.invalid(): FieldState {
    return copy(isValid = false)
}
