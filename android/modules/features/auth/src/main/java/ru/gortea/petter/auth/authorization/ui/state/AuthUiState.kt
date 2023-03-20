package ru.gortea.petter.auth.authorization.ui.state

import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal data class AuthUiState(
    val username: TextFieldState,
    val password: TextFieldState,
    val authorizeButton: ButtonState
)
