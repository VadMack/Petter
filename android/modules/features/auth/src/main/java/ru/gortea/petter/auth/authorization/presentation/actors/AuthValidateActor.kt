package ru.gortea.petter.auth.authorization.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.authorization.presentation.AuthCommand
import ru.gortea.petter.auth.authorization.presentation.AuthEvent

internal class AuthValidateActor : Actor<AuthCommand, AuthEvent> {

    override fun process(commands: Flow<AuthCommand>): Flow<AuthEvent> {
        return commands.filterIsInstance<AuthCommand.Validate>()
            .mapLatest {
                AuthEvent.Validated(
                    usernameValid = it.username.isNotBlank(),
                    pwdValid = it.pwd.isNotBlank()
                )
            }
    }
}
