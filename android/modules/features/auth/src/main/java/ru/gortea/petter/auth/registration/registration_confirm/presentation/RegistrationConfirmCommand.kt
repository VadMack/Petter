package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.data.model.RegistrationConfirmModel

internal sealed interface RegistrationConfirmCommand {
    class Confirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class RetryConfirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class Validate(val code: String) : RegistrationConfirmCommand
}
