package ru.gortea.petter.auth.registration.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.presentation.RegistrationCommand
import ru.gortea.petter.auth.registration.presentation.RegistrationEvent
import ru.gortea.petter.auth.registration.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.validation.ValidatorComposite

internal class RegistrationValidateActor(
    private val validator: ValidatorComposite<RegistrationState, RegistrationFailedReason>
) : Actor<RegistrationCommand, RegistrationEvent> {

    override fun process(commands: Flow<RegistrationCommand>): Flow<RegistrationEvent> {
        return commands
            .filterIsInstance<RegistrationCommand.Validate>()
            .mapLatest { it.state to validator.validateWithReasons(it.state) }
            .map { (state, reasons) -> RegistrationEvent.Validated(state, reasons) }
    }
}
