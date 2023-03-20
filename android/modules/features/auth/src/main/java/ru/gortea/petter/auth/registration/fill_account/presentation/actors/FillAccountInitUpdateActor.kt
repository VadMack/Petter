package ru.gortea.petter.auth.registration.fill_account.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent
import ru.gortea.petter.profile.data.remote.ProfileUpdateRepository

internal class FillAccountInitUpdateActor(
    private val repository: ProfileUpdateRepository
) : Actor<FillAccountCommand, FillAccountEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<FillAccountEvent> {
        return commands.filterIsInstance<FillAccountCommand.InitUpdateAccount>()
            .flatMapLatest { repository.get() }
            .map { FillAccountEvent.UpdateAccountStatus(it) }
    }
}
