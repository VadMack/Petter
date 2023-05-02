package ru.gortea.petter.auth.registration.registration_form.ui.state

import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.text_field.hideText

internal data class RegistrationUiState(
    val emailState: TextFieldState = TextFieldState(),
    val usernameState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState().hideText(),
    val passwordConfirmState: TextFieldState = TextFieldState().hideText(),
    val createAccountState: ButtonState = ButtonState(),
    val needToastError: Boolean = false
)
