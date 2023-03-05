package ru.gortea.petter.auth.registration.presentation.validation.validators

import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.presentation.state.isNotBlank
import ru.gortea.petter.auth.validation.Validator

internal class UsernameValidator : Validator<RegistrationState> {

    override fun validate(value: RegistrationState): Boolean {
        return value.username.isNotBlank()
    }
}
