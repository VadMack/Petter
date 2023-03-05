package ru.gortea.petter.auth.registration.presentation.state

import ru.gortea.petter.data.model.DataState

internal data class RegistrationState(
    val email: RegistrationFieldState = RegistrationFieldState(),
    val username: RegistrationFieldState = RegistrationFieldState(),
    val password: RegistrationFieldState = RegistrationFieldState().hide(),
    val passwordConfirm: RegistrationFieldState = RegistrationFieldState().hide(),
    val registrationStatus: DataState<String> = DataState.Empty
)
