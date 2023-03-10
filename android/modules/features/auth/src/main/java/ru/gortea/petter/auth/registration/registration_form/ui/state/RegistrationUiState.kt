package ru.gortea.petter.auth.registration.registration_form.ui.state

internal data class RegistrationUiState(
    val emailState: TextFieldState = TextFieldState(),
    val usernameState: TextFieldState = TextFieldState(),
    val passwordState: TextFieldState = TextFieldState().hideText(),
    val passwordConfirmState: TextFieldState = TextFieldState().hideText(),
    val createAccountState: ButtonState = ButtonState()
)
