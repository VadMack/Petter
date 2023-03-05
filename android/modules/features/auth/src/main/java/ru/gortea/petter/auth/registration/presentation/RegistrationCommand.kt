package ru.gortea.petter.auth.registration.presentation

import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.registration.presentation.state.RegistrationState

internal sealed interface RegistrationCommand {
    class Validate(val state: RegistrationState) : RegistrationCommand
    class CreateAccount(val model: RegistrationModel) : RegistrationCommand
    class RetryCreateAccount(val model: RegistrationModel) : RegistrationCommand
}
