package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.common.toTextFieldState
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

        val masked = name.run {
            if (length >= 3) {
                replaceAll(1 until lastIndex, MASK)
            } else {
                replaceAll(indices, MASK)
            }
        }

        return "$masked@$domain"
    }

    private fun String.replaceAll(
        range: IntRange,
        replacement: CharSequence
    ): String {
        return StringBuilder(length).apply {
            for (i in 0 until range.first) append(this[i])
            for (i in range) append(replacement)
            for (i in range.last until length) append(this[i])
        }.toString()
    }

    private companion object {
        private const val MASK = "*"
    }
}