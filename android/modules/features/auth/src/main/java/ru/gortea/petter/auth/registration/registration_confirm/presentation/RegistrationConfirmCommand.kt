package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel

internal sealed interface RegistrationConfirmCommand {
    class Authorize(
        val username: String,
        val pwd: String
    ) : RegistrationConfirmCommand

    class Confirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class ResendCode(val model: RegistrationEmailModel) : RegistrationConfirmCommand
    class Validate(val code: String) : RegistrationConfirmCommand

    object InitAuthorize : RegistrationConfirmCommand
    object InitConfirm : RegistrationConfirmCommand
    object InitResendCode : RegistrationConfirmCommand
}
