package ru.gortea.petter.pet.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.list.presentation.PetListCommand
import ru.gortea.petter.pet.list.presentation.PetListEvent
import ru.gortea.petter.profile.data.local.UserLocalRepository

internal class PetListUserActor(
    private val repository: UserLocalRepository
) : Actor<PetListCommand, PetListEvent> {

    override fun process(commands: Flow<PetListCommand>): Flow<PetListEvent> {
        return commands.filterIsInstance<PetListCommand.GetCurrentUser>()
            .mapLatest { repository.getCurrentUser() }
            .map { PetListEvent.CurrentUser(it) }
    }
}
