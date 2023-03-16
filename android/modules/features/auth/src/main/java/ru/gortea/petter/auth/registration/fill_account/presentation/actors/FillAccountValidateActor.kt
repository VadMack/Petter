package ru.gortea.petter.auth.registration.fill_account.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason
import ru.gortea.petter.auth.validation.ValidatorComposite

internal class FillAccountValidateActor(
    private val validator: ValidatorComposite<FillAccountState, FillAccountFailedReason>
) : Actor<FillAccountCommand, FillAccountEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<FillAccountEvent> {
        return commands.filterIsInstance<FillAccountCommand.Validate>()
            .mapLatest { validator.validateWithReasons(it.state) }
            .map { FillAccountEvent.Validated(it) }
    }
}
