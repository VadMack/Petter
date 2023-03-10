package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.auth.data.model.RegistrationModel

internal sealed interface RegistrationCommand {
    class Validate(val state: RegistrationState) : RegistrationCommand
    class CreateAccount(val model: RegistrationModel) : RegistrationCommand
    class RetryCreateAccount(val model: RegistrationModel) : RegistrationCommand
}
