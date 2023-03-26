package ru.gortea.petter.auth.registration.registration_form.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.ui.state.RegistrationUiState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.email

internal class RegistrationUiStateMapper : UiStateMapper<RegistrationState, RegistrationUiState> {

    override fun map(state: RegistrationState): RegistrationUiState {
        return RegistrationUiState(
            emailState = state.email.toTextFieldState().email(),
            usernameState = state.username.toTextFieldState(),
            passwordState = state.password.toTextFieldState(),
            passwordConfirmState = state.passwordConfirm.toTextFieldState(),
            createAccountState = state.registrationStatus.toButtonState()
        )
    }

    private fun DataState<*>.toButtonState(): ButtonState {
        return ButtonState(
            isLoading = this is DataState.Loading
        )
    }
}
