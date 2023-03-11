package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.auth.registration.common.RegistrationFieldState
import ru.gortea.petter.auth.registration.common.hide
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.model.UserModel

internal data class RegistrationState(
    val email: RegistrationFieldState = RegistrationFieldState("jmax@mail.ru"),
    val username: RegistrationFieldState = RegistrationFieldState("jmax"),
    val password: RegistrationFieldState = RegistrationFieldState("1234").hide(),
    val passwordConfirm: RegistrationFieldState = RegistrationFieldState("1234").hide(),
    val registrationStatus: DataState<UserModel> = DataState.Empty
)
