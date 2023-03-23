package ru.gortea.petter.profile.edit.presentation.validation.reason

import ru.gortea.petter.arch.android.util.validation.FailedReasonMapper
import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason.INVALID_CITY
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason.INVALID_COUNTRY
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason.INVALID_NAME
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason.INVALID_STREET
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason.NONE
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditCityValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditCountryValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditNameValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditStreetValidator

internal class ProfileEditFailedReasonMapper : FailedReasonMapper<ProfileEditFailedReason> {

    override fun map(validator: Validator<*>): ProfileEditFailedReason {
        return when (validator) {
            is ProfileEditNameValidator -> INVALID_NAME
            is ProfileEditCountryValidator -> INVALID_COUNTRY
            is ProfileEditCityValidator -> INVALID_CITY
            is ProfileEditStreetValidator -> INVALID_STREET
            else -> NONE
        }
    }
}
