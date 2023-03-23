package ru.gortea.petter.profile.edit.presentation.validation.validators

import ru.gortea.petter.arch.android.util.validation.Validator
import ru.gortea.petter.profile.edit.presentation.ProfileEditState

internal class ProfileEditNameValidator : Validator<ProfileEditState> {

    override fun validate(value: ProfileEditState): Boolean {
        return value.nameFieldState.text.isNotBlank()
    }
}
