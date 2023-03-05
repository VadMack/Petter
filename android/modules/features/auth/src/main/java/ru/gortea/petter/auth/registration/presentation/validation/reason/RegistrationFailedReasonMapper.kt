package ru.gortea.petter.auth.registration.presentation.validation.reason

import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReason.*
import ru.gortea.petter.auth.registration.presentation.validation.validators.EmailValidator
import ru.gortea.petter.auth.registration.presentation.validation.validators.PasswordValidator
import ru.gortea.petter.auth.validation.FailedReasonMapper
import ru.gortea.petter.auth.validation.Validator

internal class RegistrationFailedReasonMapper :
    FailedReasonMapper<RegistrationFailedReason> {

    override fun map(validator: Validator<*>): RegistrationFailedReason {
        return when (validator) {
            is EmailValidator -> INVALID_EMAIL
            is PasswordValidator -> PASSWORDS_ARE_DIFFERENT
            else -> NONE
        }
    }
}
