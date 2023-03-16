package ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators

import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.validation.Validator

internal class FillAccountNameValidator : Validator<FillAccountState> {

    override fun validate(value: FillAccountState): Boolean {
        return value.nameFieldState.text.isNotBlank()
    }
}
