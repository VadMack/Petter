package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface RegistrationConfirmCommand {
    class Authorize(val model: CredsAuthorizationModel) : RegistrationConfirmCommand
    class Confirm(val model: RegistrationConfirmModel) : RegistrationConfirmCommand
    class ResendCode(val model: RegistrationEmailModel) : RegistrationConfirmCommand
    class Validate(val code: String) : RegistrationConfirmCommand
    class UpdateUser(val user: UserModel) : RegistrationConfirmCommand

    object InitAuthorize : RegistrationConfirmCommand
    object InitConfirm : RegistrationConfirmCommand
    object InitResendCode : RegistrationConfirmCommand
}
