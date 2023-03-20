package ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators

import ru.gortea.petter.auth.common.isBlank
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.validation.Validator

internal class FillAccountStreetValidator : Validator<FillAccountState> {

    override fun validate(value: FillAccountState): Boolean {
        if (value.streetFieldState.text.isNotBlank()) return true

        return value.houseFieldState.isBlank()
    }
}
