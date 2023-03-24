package ru.gortea.petter.profile.presentation

import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal data class ProfileState(
    val isCurrentUser: Boolean = false,
    val userModelStatus: DataState<UserModel> = DataState.Empty
)
