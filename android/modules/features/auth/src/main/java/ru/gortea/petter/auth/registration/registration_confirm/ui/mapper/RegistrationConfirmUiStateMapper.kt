package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.registration.common.toTextFieldState
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.ui_kit.button.ButtonState

internal class RegistrationConfirmUiStateMapper :
    UiStateMapper<RegistrationConfirmState, RegistrationConfirmUiState> {

    override fun map(state: RegistrationConfirmState): RegistrationConfirmUiState {
        return RegistrationConfirmUiState(
            codeState = state.codeState.toTextFieldState(),
            sendCodeButtonState = state.toSendCodeButtonState(),
            maskedEmail = state.email.mask()
        )
    }

    private fun RegistrationConfirmState.toSendCodeButtonState(): ButtonState {
        return ButtonState(
            isLoading = confirmationStatus is DataState.Loading || authStatus is DataState.Loading
        )
    }

    private fun String.mask(): String {
        val (name, domain) = split("@")
        val masked = if (name.length >= 3) {
            name.run { replaceRange(1..lastIndex, MASK) }
        } else {
            name.run { replaceRange(0..lastIndex, MASK) }
        }

        return "$masked@$domain"
    }

    private companion object {
        private const val MASK = "*"
    }
}
