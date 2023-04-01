package ru.gortea.petter.pet.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.pet.list.presentation.PetListCommand
import ru.gortea.petter.pet.list.presentation.PetListEvent

internal class PetListInitActor(
    private val petListRepository: PetListRepository
) : Actor<PetListCommand, PetListEvent> {
    override fun process(commands: Flow<PetListCommand>): Flow<PetListEvent> {
        return commands.filterIsInstance<PetListCommand.InitPetList>()
            .flatMapLatest { petListRepository.get() }
            .map { PetListEvent.PetListLoadingStatus(it) }
    }
}
