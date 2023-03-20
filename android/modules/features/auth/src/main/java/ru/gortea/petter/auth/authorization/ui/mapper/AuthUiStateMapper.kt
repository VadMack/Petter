package ru.gortea.petter.auth.authorization.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.authorization.presentation.AuthState
import ru.gortea.petter.auth.authorization.ui.state.AuthUiState
import ru.gortea.petter.auth.common.toTextFieldState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.ui_kit.button.ButtonState

internal class AuthUiStateMapper : UiStateMapper<AuthState, AuthUiState> {
    override fun map(state: AuthState): AuthUiState {
        return AuthUiState(
            username = state.username.toTextFieldState(),
            password = state.password.toTextFieldState(),
            authorizeButton = state.toButtonState()
        )
    }

    private fun AuthState.toButtonState(): ButtonState {
        return ButtonState(
            isLoading = authStatus.isLoading || (authStatus.isContent && !userUpdated)
        )
    }
}
