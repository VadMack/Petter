package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.data.model.AuthorizationModel
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel

internal sealed interface RegistrationConfirmCommand {
    class Authorize(val model: AuthorizationModel) : RegistrationConfirmCommand
    class Confirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class RetryConfirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class ResendCode(val model: RegistrationEmailModel) : RegistrationConfirmCommand
    class RetryResendCode(val model: RegistrationEmailModel) : RegistrationConfirmCommand
    class Validate(val code: String) : RegistrationConfirmCommand
}
