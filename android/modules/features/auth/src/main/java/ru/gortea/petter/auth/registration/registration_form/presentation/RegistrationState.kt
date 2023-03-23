package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.hide
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal data class RegistrationState(
    val email: FieldState = FieldState(),
    val username: FieldState = FieldState(),
    val password: FieldState = FieldState().hide(),
    val passwordConfirm: FieldState = FieldState().hide(),
    val registrationStatus: DataState<UserModel> = DataState.Empty
)
