package ru.gortea.petter.auth.registration.presentation.validation.reason

import ru.gortea.petter.auth.validation.FailedReason

internal enum class RegistrationFailedReason : FailedReason {
    NONE,
    INVALID_USERNAME,
    INVALID_EMAIL,
    PASSWORDS_ARE_DIFFERENT
}
