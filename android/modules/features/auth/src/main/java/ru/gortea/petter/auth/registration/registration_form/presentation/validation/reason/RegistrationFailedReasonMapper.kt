package ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason

import ru.gortea.petter.arch.android.util.validation.FailedReasonMapper
import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_EMAIL
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_USERNAME
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.NONE
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.PASSWORDS_ARE_DIFFERENT
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.EmailValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.PasswordValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.UsernameValidator

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
