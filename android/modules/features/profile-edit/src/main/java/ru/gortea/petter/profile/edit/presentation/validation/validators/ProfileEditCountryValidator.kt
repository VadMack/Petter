package ru.gortea.petter.profile.edit.presentation.validation.validators

import ru.gortea.petter.arch.android.util.isBlank
import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.profile.edit.presentation.ProfileEditState

internal class ProfileEditCountryValidator : Validator<ProfileEditState> {

    override fun validate(value: ProfileEditState): Boolean {
        if (value.countryFieldState.text.isNotBlank()) return true

        return value.run {
            cityFieldState.isBlank() && streetFieldState.isBlank() && houseFieldState.isBlank()
        }
    }
}
