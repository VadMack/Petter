package ru.gortea.petter.auth.registration.presentation.actors

import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.presentation.RegistrationCommand
import ru.gortea.petter.auth.registration.presentation.RegistrationEvent

internal class RegistrationCreateAccountActor(
    private val repository: RegistrationRepository
) : Actor<RegistrationCommand, RegistrationEvent> {

    override fun process(commands: Flow<RegistrationCommand>): Flow<RegistrationEvent> {
        return commands
            .filterIsInstance<RegistrationCommand.CreateAccount>()
            .flatMapLatest { repository.getRegistrationSource(it.model) }
            .map { RegistrationEvent.AccountCreateProcess(it) }
    }
}
