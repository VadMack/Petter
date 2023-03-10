package ru.gortea.petter.auth.registration.registration_form.presentation.actors

import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationCommand
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationEvent

internal class RegistrationRetryCreateAccountActor(
    private val repository: RegistrationRepository
) : Actor<RegistrationCommand, RegistrationEvent> {

    override fun process(commands: Flow<RegistrationCommand>): Flow<RegistrationEvent> {
        return commands.filterIsInstance<RegistrationCommand.RetryCreateAccount>()
            .mapLatest { repository.retryCreateAccount(it.model) }
            .flatMapMerge { emptyFlow() }
    }
}
