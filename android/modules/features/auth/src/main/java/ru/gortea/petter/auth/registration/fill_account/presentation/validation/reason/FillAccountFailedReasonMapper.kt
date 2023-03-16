package ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason

import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason.INVALID_CITY
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason.INVALID_COUNTRY
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason.INVALID_NAME
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason.INVALID_STREET
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason.NONE
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountCityValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountCountryValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountNameValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountStreetValidator
import ru.gortea.petter.auth.validation.FailedReasonMapper
import ru.gortea.petter.auth.validation.Validator

internal class FillAccountFailedReasonMapper : FailedReasonMapper<FillAccountFailedReason> {

    override fun map(validator: Validator<*>): FillAccountFailedReason {
        return when (validator) {
            is FillAccountNameValidator -> INVALID_NAME
            is FillAccountCountryValidator -> INVALID_COUNTRY
            is FillAccountCityValidator -> INVALID_CITY
            is FillAccountStreetValidator -> INVALID_STREET
            else -> NONE
        }
    }
}
