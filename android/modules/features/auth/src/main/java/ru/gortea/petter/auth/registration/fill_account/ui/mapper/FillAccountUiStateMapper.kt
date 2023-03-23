package ru.gortea.petter.auth.registration.fill_account.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.auth.common.toTextFieldState
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.registration.fill_account.ui.state.FillAccountUiState
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.ui_kit.button.ButtonState

internal class FillAccountUiStateMapper : UiStateMapper<FillAccountState, FillAccountUiState> {

    override fun map(state: FillAccountState): FillAccountUiState {
        return FillAccountUiState(
            avatar = state.avatar,
            name = state.nameFieldState.toTextFieldState(),
            country = state.countryFieldState.toTextFieldState(),
            city = state.cityFieldState.toTextFieldState(),
            street = state.streetFieldState.toTextFieldState(),
            house = state.houseFieldState.toTextFieldState(),
            saveButton = state.toButtonState()
        )
    }

    private fun FillAccountState.toButtonState(): ButtonState {
        return ButtonState(
            isLoading = userUpdateStatus.isLoading
        )
    }
}
