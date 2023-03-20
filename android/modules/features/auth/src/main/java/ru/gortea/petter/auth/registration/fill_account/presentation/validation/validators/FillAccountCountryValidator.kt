package ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators

import ru.gortea.petter.auth.common.isBlank
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.validation.Validator

internal class FillAccountCountryValidator : Validator<FillAccountState> {

    override fun validate(value: FillAccountState): Boolean {
        if (value.countryFieldState.text.isNotBlank()) return true

        return value.run {
            cityFieldState.isBlank() && streetFieldState.isBlank() && houseFieldState.isBlank()
        }
    }
}
