package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.registration.common.RegistrationFieldState
import ru.gortea.petter.data.model.DataState

internal data class RegistrationConfirmState(
    val userId: String,
    val email: String,
    val codeState: RegistrationFieldState = RegistrationFieldState(),
    val confirmationStatus: DataState<Unit> = DataState.Empty
)
