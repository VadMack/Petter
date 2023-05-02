package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isFail
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.network.errors.isConnectionError
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.number

internal class RegistrationConfirmUiStateMapper :
    UiStateMapper<RegistrationConfirmState, RegistrationConfirmUiState> {

    override fun map(state: RegistrationConfirmState): RegistrationConfirmUiState {
        return RegistrationConfirmUiState(
            codeState = state.codeState.toTextFieldState().number(),
            sendCodeButtonState = state.toSendCodeButtonState(),
            maskedEmail = state.email.mask(),
            needErrorToast = state.needToastError()
        )
    }

    private fun RegistrationConfirmState.toSendCodeButtonState(): ButtonState {
        return ButtonState(
            isLoading = confirmationStatus.isLoading || authStatus.isLoading
        )
    }

    private fun String.mask(): String {
        val (name, domain) = split("@")

        val masked = "${name[0]}$MASK${name.last()}"

        return "$masked@$domain"
    }

    private fun RegistrationConfirmState.needToastError(): Boolean {
        val byConfirmation = confirmationStatus is DataState.Fail
                && confirmationStatus.reason.isConnectionError()
        val byResendCode = resendCodeStatus.isFail
        val byAuth = authStatus.isFail

        val enableToast = byConfirmation || byResendCode || byAuth
        val disableToast = confirmationStatus.isLoading || resendCodeStatus.isLoading || authStatus.isLoading

        return enableToast && !disableToast
    }

    private companion object {
        private const val MASK = "***"
    }
}
