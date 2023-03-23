package ru.gortea.petter.profile.edit.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.profile.edit.presentation.ProfileEditState
import ru.gortea.petter.profile.edit.ui.state.ProfileEditUiState
import ru.gortea.petter.ui_kit.button.ButtonState

internal class ProfileEditUiStateMapper : UiStateMapper<ProfileEditState, ProfileEditUiState> {

    override fun map(state: ProfileEditState): ProfileEditUiState {
        return ProfileEditUiState(
            avatar = state.avatar,
            name = state.nameFieldState.toTextFieldState(),
            country = state.countryFieldState.toTextFieldState(),
            city = state.cityFieldState.toTextFieldState(),
            street = state.streetFieldState.toTextFieldState(),
            house = state.houseFieldState.toTextFieldState(),
            saveButton = state.toButtonState()
        )
    }

    private fun ProfileEditState.toButtonState(): ButtonState {
        return ButtonState(
            isLoading = userUpdateStatus.isLoading
        )
    }
}
