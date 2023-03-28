package ru.gortea.petter.auth.registration.registration_confirm.ui.state

import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.text_field.number

internal data class RegistrationConfirmUiState(
    val codeState: TextFieldState = TextFieldState().number(),
    val sendCodeButtonState: ButtonState = ButtonState(),
    val maskedEmail: String = "****"
)
