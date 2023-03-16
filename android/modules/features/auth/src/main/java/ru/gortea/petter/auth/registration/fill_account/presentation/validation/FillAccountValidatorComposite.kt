package ru.gortea.petter.auth.registration.fill_account.presentation.validation

import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReasonMapper
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountCityValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountCountryValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountNameValidator
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.validators.FillAccountStreetValidator
import ru.gortea.petter.auth.validation.ValidatorComposite

internal class FillAccountValidatorComposite :
    ValidatorComposite<FillAccountState, FillAccountFailedReason>(
        FillAccountFailedReasonMapper(),
        FillAccountNameValidator(),
        FillAccountCountryValidator(),
        FillAccountCityValidator(),
        FillAccountStreetValidator()
    )
