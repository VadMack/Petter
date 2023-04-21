package ru.gortea.petter.auth.authorization.presentation

internal sealed interface AuthCommand {
    class Authorize(val username: String, val pwd: String) : AuthCommand
    class Validate(val username: String, val pwd: String) : AuthCommand

    object InitAuthorize : AuthCommand
}
