package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.number

internal class RegistrationConfirmUiStateMapper :
    UiStateMapper<RegistrationConfirmState, RegistrationConfirmUiState> {

    override fun map(state: RegistrationConfirmState): RegistrationConfirmUiState {
        return RegistrationConfirmUiState(
            codeState = state.codeState.toTextFieldState().number(),
            sendCodeButtonState = state.toSendCodeButtonState(),
            maskedEmail = state.email.mask()
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

    private companion object {
        private const val MASK = "***"
    }
}
