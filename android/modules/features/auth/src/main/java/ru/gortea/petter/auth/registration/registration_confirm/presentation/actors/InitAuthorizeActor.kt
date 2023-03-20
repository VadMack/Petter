package ru.gortea.petter.auth.registration.registration_confirm.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.AuthorizationRepository
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent

internal class InitAuthorizeActor(
    private val repository: AuthorizationRepository
) : Actor<RegistrationConfirmCommand, RegistrationConfirmEvent> {

    override fun process(commands: Flow<RegistrationConfirmCommand>): Flow<RegistrationConfirmEvent> {
        return commands.filterIsInstance<RegistrationConfirmCommand.InitAuthorize>()
            .flatMapLatest { repository.get() }
            .map { RegistrationConfirmEvent.AuthorizationStatus(it) }
    }
}
