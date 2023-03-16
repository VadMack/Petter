package ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason

import ru.gortea.petter.auth.validation.FailedReason

internal enum class FillAccountFailedReason : FailedReason {
    INVALID_NAME,
    INVALID_COUNTRY,
    INVALID_CITY,
    INVALID_STREET,
    NONE
}
