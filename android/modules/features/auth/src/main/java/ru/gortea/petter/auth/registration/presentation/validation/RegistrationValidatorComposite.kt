package ru.gortea.petter.auth.registration.presentation.validation

import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReasonMapper
import ru.gortea.petter.auth.registration.presentation.validation.validators.EmailValidator
import ru.gortea.petter.auth.registration.presentation.validation.validators.PasswordValidator
import ru.gortea.petter.auth.registration.presentation.validation.validators.UsernameValidator
import ru.gortea.petter.auth.validation.ValidatorComposite

internal class RegistrationValidatorComposite :
    ValidatorComposite<RegistrationState, RegistrationFailedReason>(
        failedReasonMapper = RegistrationFailedReasonMapper(),
        EmailValidator(),
        UsernameValidator(),
        PasswordValidator()
    )


