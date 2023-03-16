package ru.gortea.petter.auth.registration.registration_confirm.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.data.RegistrationRepository
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent

internal class InitResendCodeActor(
    private val repository: RegistrationRepository
) : Actor<RegistrationConfirmCommand, RegistrationConfirmEvent> {

    override fun process(commands: Flow<RegistrationConfirmCommand>): Flow<RegistrationConfirmEvent> {
        return commands.filterIsInstance<RegistrationConfirmCommand.InitResendCode>()
            .flatMapLatest { repository.initResendConfirmCode() }
            .map { RegistrationConfirmEvent.ResendCodeStatus(it) }
    }
}
