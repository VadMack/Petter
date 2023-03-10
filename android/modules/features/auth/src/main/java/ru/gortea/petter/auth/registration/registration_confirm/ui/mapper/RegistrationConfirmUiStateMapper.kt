package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal class RegistrationConfirmUiStateMapper :
    UiStateMapper<RegistrationConfirmState, RegistrationConfirmUiState> {

    override fun map(state: RegistrationConfirmState): RegistrationConfirmUiState {
        return RegistrationConfirmUiState(
            codeState = TextFieldState(
                text = state.codeState.text,
                isIncorrect = !state.codeState.isValid
            ),
            sendCodeButtonState = state.confirmationStatus.toButtonState(),
            maskedEmail = state.email.mask()
        )
    }

    private fun DataState<Unit>.toButtonState(): ButtonState {
        return ButtonState(isLoading = this is DataState.Loading)
    }

    private fun String.mask(): String {
        val (name, domain) = split("@")
        val masked = if (name.length >= 3) {
            name.run { replaceRange(1 until lastIndex, MASK) }
        } else {
            name.run { replaceRange(0..lastIndex, MASK) }
        }

        return "$masked@$domain"
    }

    private companion object {
        private const val MASK = "*"
    }
}
