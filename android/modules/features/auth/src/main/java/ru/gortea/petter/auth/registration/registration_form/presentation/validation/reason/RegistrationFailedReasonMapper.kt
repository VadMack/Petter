package ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason

import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.*
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.EmailValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.PasswordValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.UsernameValidator
import ru.gortea.petter.auth.validation.FailedReasonMapper
import ru.gortea.petter.auth.validation.Validator

internal class RegistrationFailedReasonMapper : FailedReasonMapper<RegistrationFailedReason> {

    override fun map(validator: Validator<*>): RegistrationFailedReason {
        return when (validator) {
            is EmailValidator -> INVALID_EMAIL
            is UsernameValidator -> INVALID_USERNAME
            is PasswordValidator -> PASSWORDS_ARE_DIFFERENT
            else -> NONE
        }
    }
}
