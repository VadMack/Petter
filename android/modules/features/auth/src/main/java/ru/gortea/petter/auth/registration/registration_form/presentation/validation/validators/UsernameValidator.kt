package ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators

import ru.gortea.petter.arch.android.util.isNotBlank
import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState

internal class UsernameValidator : Validator<RegistrationState> {

    override fun validate(value: RegistrationState): Boolean {
        return value.username.isNotBlank()
    }
}
