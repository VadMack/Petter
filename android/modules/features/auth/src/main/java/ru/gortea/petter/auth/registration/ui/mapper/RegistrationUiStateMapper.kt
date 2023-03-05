package ru.gortea.petter.auth.registration.ui.mapper

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.registration.presentation.state.RegistrationFieldState
import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.ui.state.RegistrationUiState
import ru.gortea.petter.auth.registration.ui.state.TextFieldState

internal class RegistrationUiStateMapper : UiStateMapper<RegistrationState, RegistrationUiState> {

    override fun map(state: RegistrationState): RegistrationUiState {
        return RegistrationUiState(
            emailState = state.email.toTextFieldState(),
            usernameState = state.username.toTextFieldState(),
            passwordState = state.password.toTextFieldState(),
            passwordConfirmState = state.passwordConfirm.toTextFieldState()
        )
    }

    private fun RegistrationFieldState.toTextFieldState(): TextFieldState {
        val transform = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
        return TextFieldState(
            text = text,
            isIncorrect = !isValid,
            visualTransformation = transform
        )
    }
}
