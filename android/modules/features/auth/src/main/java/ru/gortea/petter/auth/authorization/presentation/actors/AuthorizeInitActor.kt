package ru.gortea.petter.auth.authorization.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.authorization.presentation.AuthCommand
import ru.gortea.petter.auth.authorization.presentation.AuthEvent
import ru.gortea.petter.auth.data.AuthorizationRepository

internal class AuthorizeInitActor(
    private val repository: AuthorizationRepository
) : Actor<AuthCommand, AuthEvent> {

    override fun process(commands: Flow<AuthCommand>): Flow<AuthEvent> {
        return commands.filterIsInstance<AuthCommand.InitAuthorize>()
            .flatMapLatest { repository.get() }
            .mapLatest { AuthEvent.AuthorizationStatus(it) }
    }
}
