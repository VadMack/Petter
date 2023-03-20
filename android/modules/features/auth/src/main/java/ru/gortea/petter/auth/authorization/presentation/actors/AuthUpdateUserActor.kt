package ru.gortea.petter.auth.authorization.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.authorization.presentation.AuthCommand
import ru.gortea.petter.auth.authorization.presentation.AuthEvent
import ru.gortea.petter.profile.data.local.UserLocalRepository

internal class AuthUpdateUserActor(
    private val repository: UserLocalRepository
) : Actor<AuthCommand, AuthEvent> {

    override fun process(commands: Flow<AuthCommand>): Flow<AuthEvent> {
        return commands.filterIsInstance<AuthCommand.UpdateUser>()
            .mapLatest { repository.updateCurrentUser(it.user) }
            .map { AuthEvent.UserUpdated }
    }
}
