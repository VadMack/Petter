package ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators

import ru.gortea.petter.auth.registration.registration_form.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.presentation.state.isNotBlank
import ru.gortea.petter.auth.validation.Validator

internal class PasswordValidator : Validator<RegistrationState> {

    override fun validate(value: RegistrationState): Boolean {
        val arePasswordsTheSame = value.run { password.text == passwordConfirm.text }
        return value.run {
            password.isNotBlank() && passwordConfirm.isNotBlank() && arePasswordsTheSame
        }
    }
}
