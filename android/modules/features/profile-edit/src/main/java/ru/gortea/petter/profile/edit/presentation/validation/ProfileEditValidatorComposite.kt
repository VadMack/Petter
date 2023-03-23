package ru.gortea.petter.profile.edit.presentation.validation

import ru.gortea.petter.arch.android.util.validation.ValidatorComposite
import ru.gortea.petter.profile.edit.presentation.ProfileEditState
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReasonMapper
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditCityValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditCountryValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditNameValidator
import ru.gortea.petter.profile.edit.presentation.validation.validators.ProfileEditStreetValidator

internal class ProfileEditValidatorComposite :
    ValidatorComposite<ProfileEditState, ProfileEditFailedReason>(
        ProfileEditFailedReasonMapper(),
        ProfileEditNameValidator(),
        ProfileEditCountryValidator(),
        ProfileEditCityValidator(),
        ProfileEditStreetValidator()
    )
