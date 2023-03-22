package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.auth.data.model.CredsAuthorizationModel

internal sealed interface AuthCommand {
    class Authorize(val credentials: CredsAuthorizationModel): AuthCommand
    class Validate(val username: String, val pwd: String): AuthCommand

    object InitAuthorize : AuthCommand
}
