package ru.gortea.petter.profile.edit.presentation.validation.reason

import ru.gortea.petter.arch.android.util.validation.FailedReason

internal enum class ProfileEditFailedReason : FailedReason {
    INVALID_NAME,
    INVALID_COUNTRY,
    INVALID_CITY,
    INVALID_STREET,
    NONE
}
