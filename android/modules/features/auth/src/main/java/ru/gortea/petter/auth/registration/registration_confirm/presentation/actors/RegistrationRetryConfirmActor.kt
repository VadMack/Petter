package ru.gortea.petter.auth.registration.registration_confirm.presentation.actors

import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent

internal class RegistrationRetryConfirmActor(
    private val repository: RegistrationRepository
) : Actor<RegistrationConfirmCommand, RegistrationConfirmEvent> {

    override fun process(commands: Flow<RegistrationConfirmCommand>): Flow<RegistrationConfirmEvent> {
        return commands.filterIsInstance<RegistrationConfirmCommand.RetryConfirm>()
            .mapLatest { repository.retryRegistrationConfirm(it.model) }
            .flatMapMerge { emptyFlow() }
    }
}
