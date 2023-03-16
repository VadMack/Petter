package ru.gortea.petter.auth.registration.fill_account.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent
import ru.gortea.petter.profile.data.ProfileUpdateRepository

internal class FillAccountUpdateActor(
    private val repository: ProfileUpdateRepository
) : Actor<FillAccountCommand, FillAccountEvent> {

    override fun process(commands: Flow<FillAccountCommand>): Flow<FillAccountEvent> {
        return commands.filterIsInstance<FillAccountCommand.UpdateAccount>()
            .mapLatest { repository.invalidate(it.user) }
            .flatMapMerge { emptyFlow() }
    }
}
