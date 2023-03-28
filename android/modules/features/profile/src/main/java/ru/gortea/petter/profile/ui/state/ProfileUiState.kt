package ru.gortea.petter.profile.ui.state

import ru.gortea.petter.data.model.DataState

internal data class ProfileUiState(
    val hasProfileMenu: Boolean,
    val userState: DataState<ProfileUiModel>
)
