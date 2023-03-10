package ru.gortea.petter.auth.registration.registration_form.ui.mapper

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.registration.registration_form.presentation.state.RegistrationFieldState
import ru.gortea.petter.auth.registration.registration_form.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.ui.state.ButtonState
import ru.gortea.petter.auth.registration.registration_form.ui.state.RegistrationUiState
import ru.gortea.petter.auth.registration.registration_form.ui.state.TextFieldState
import ru.gortea.petter.data.model.DataState

internal class RegistrationUiStateMapper : UiStateMapper<RegistrationState, RegistrationUiState> {

    override fun map(state: RegistrationState): RegistrationUiState {
        return RegistrationUiState(
            emailState = state.email.toTextFieldState(),
            usernameState = state.username.toTextFieldState(),
            passwordState = state.password.toTextFieldState(),
            passwordConfirmState = state.passwordConfirm.toTextFieldState(),
            createAccountState = state.registrationStatus.toButtonState()
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

    private fun DataState<*>.toButtonState(): ButtonState {
        return ButtonState(
            isLoading = this is DataState.Loading
        )
    }
}
