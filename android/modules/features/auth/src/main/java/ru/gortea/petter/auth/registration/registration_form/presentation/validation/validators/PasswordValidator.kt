package ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators

import ru.gortea.petter.arch.android.util.isNotBlank
import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState

internal class PasswordValidator : Validator<RegistrationState> {

    override fun validate(value: RegistrationState): Boolean {
        val arePasswordsTheSame = value.run { password.text == passwordConfirm.text }
        return value.run {
            password.isNotBlank() && passwordConfirm.isNotBlank() && arePasswordsTheSame
        }
    }
}
