package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.auth.registration.common.FieldState
import ru.gortea.petter.auth.registration.common.hide
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.model.UserModel

internal data class RegistrationState(
    val email: FieldState = FieldState("jmax@mail.ru"),
    val username: FieldState = FieldState("jmax"),
    val password: FieldState = FieldState("1234").hide(),
    val passwordConfirm: FieldState = FieldState("1234").hide(),
    val registrationStatus: DataState<UserModel> = DataState.Empty
)
