package ru.gortea.petter.auth.registration.presentation.validation.validators

import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.presentation.state.isNotBlank
import ru.gortea.petter.auth.validation.Validator

internal class EmailValidator : Validator<RegistrationState> {
    private val regex = Regex("^(?=.{1,64}@)[A-Za-z\\d_-]+(\\.[A-Za-z\\d_-]+)*@" +
            "[^-][A-Za-z\\d-]+(\\.[A-Za-z\\d-]+)*(\\.[A-Za-z]{2,})$")

    override fun validate(value: RegistrationState): Boolean {
        return value.email.isNotBlank() && regex.containsMatchIn(value.email.text)
    }
}
