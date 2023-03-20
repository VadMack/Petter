package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface AuthCommand {
    class Authorize(val credentials: CredsAuthorizationModel): AuthCommand
    class Validate(val username: String, val pwd: String): AuthCommand
    class UpdateUser(val user: UserModel): AuthCommand

    object InitAuthorize : AuthCommand
}
