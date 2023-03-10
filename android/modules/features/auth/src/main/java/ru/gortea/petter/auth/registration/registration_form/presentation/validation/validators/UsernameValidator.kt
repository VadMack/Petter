package ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators

import ru.gortea.petter.auth.registration.registration_form.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.presentation.state.isNotBlank
import ru.gortea.petter.auth.validation.Validator

internal class UsernameValidator : Validator<RegistrationState> {

    override fun validate(value: RegistrationState): Boolean {
        return value.username.isNotBlank()
    }
}