package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.registration.common.FieldState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.model.UserModel

internal data class RegistrationConfirmState(
    val userId: String,
    val email: String,
    val username: String,
    val password: String,
    val codeState: FieldState = FieldState(),
    val confirmationStatus: DataState<Unit> = DataState.Empty,
    val resendCodeStatus: DataState<UserModel> = DataState.Empty,
    val authStatus: DataState<UserModel> = DataState.Empty
)
