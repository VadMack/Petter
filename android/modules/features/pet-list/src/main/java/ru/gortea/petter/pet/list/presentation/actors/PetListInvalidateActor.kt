package ru.gortea.petter.pet.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.pet.list.presentation.PetListCommand
import ru.gortea.petter.pet.list.presentation.PetListEvent

internal class PetListInvalidateActor(
    private val petListRepository: PetListRepository
) : Actor<PetListCommand, PetListEvent> {

    override fun process(commands: Flow<PetListCommand>): Flow<PetListEvent> {
        return commands.filterIsInstance<PetListCommand.Invalidate>()
            .mapLatest { petListRepository.invalidate(it.key, it.refresh) }
            .flatMapMerge { emptyFlow() }
    }
}
