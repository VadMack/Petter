package ru.gortea.petter.auth.registration.registration_form.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationCommand
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationEvent

internal class RegistrationInitCreateAccountActor(
    private val repository: RegistrationRepository
) : Actor<RegistrationCommand, RegistrationEvent> {

    override fun process(commands: Flow<RegistrationCommand>): Flow<RegistrationEvent> {
        return commands.filterIsInstance<RegistrationCommand.InitCreateAccount>()
            .flatMapLatest { repository.initCreateAccount() }
            .map { RegistrationEvent.AccountCreateStatus(it) }
    }
}
