package ru.gortea.petter.auth.registration.presentation.state

import ru.gortea.petter.data.model.DataState

internal data class RegistrationState(
    val email: RegistrationFieldState = RegistrationFieldState("jmax@mail.ru"),
    val username: RegistrationFieldState = RegistrationFieldState("jmax"),
    val password: RegistrationFieldState = RegistrationFieldState("1234").hide(),
    val passwordConfirm: RegistrationFieldState = RegistrationFieldState("1234").hide(),
    val registrationStatus: DataState<String> = DataState.Empty
)
