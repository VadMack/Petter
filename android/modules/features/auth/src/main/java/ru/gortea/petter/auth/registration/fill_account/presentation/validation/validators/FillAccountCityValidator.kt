package ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators

import ru.gortea.petter.auth.registration.common.isBlank
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.validation.Validator

internal class FillAccountCityValidator : Validator<FillAccountState> {

    override fun validate(value: FillAccountState): Boolean {
        if (value.cityFieldState.text.isNotBlank()) return true

        return value.run { streetFieldState.isBlank() && houseFieldState.isBlank() }
    }
}
