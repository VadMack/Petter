package ru.gortea.petter.auth.registration.registration_confirm.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.common.toTextFieldState
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.data.model.isLoading
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
        val isUpdatingUser = authStatus.isContent && !isUserUpdated
        return ButtonState(
            isLoading = confirmationStatus.isLoading || authStatus.isLoading || isUpdatingUser
        )
    }

    private fun String.mask(): String {
        val (name, domain) = split("@")

        val masked = name.run {
            if (length >= 3) {
                replaceAll(1..lastIndex, MASK)
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
        return StringBuilder(length).also {
            for (i in 0 until range.first) it.append(this[i])
            for (i in range) it.append(replacement)
            for (i in range.last until length) it.append(this[i])
        }.toString()
    }

    private companion object {
        private const val MASK = "*"
    }
}
