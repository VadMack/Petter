package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.auth.common.FieldState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal data class AuthState(
    val username: FieldState = FieldState(),
    val password: FieldState = FieldState(isVisible = false),
    val authStatus: DataState<UserModel> = DataState.Empty
)
