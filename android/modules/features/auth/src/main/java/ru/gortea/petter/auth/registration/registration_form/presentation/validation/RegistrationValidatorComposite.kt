package ru.gortea.petter.auth.registration.registration_form.presentation.validation

import ru.gortea.petter.arch.android.util.validation.ValidatorComposite
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReasonMapper
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.EmailValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.PasswordValidator
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.validators.UsernameValidator

internal class RegistrationValidatorComposite :
    ValidatorComposite<RegistrationState, RegistrationFailedReason>(
        failedReasonMapper = RegistrationFailedReasonMapper(),
        EmailValidator(),
        UsernameValidator(),
        PasswordValidator()
    )


