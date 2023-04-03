package ru.gortea.petter.pet.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.list.presentation.PetListCommand
import ru.gortea.petter.pet.list.presentation.PetListEvent

internal class PetListLikeActor(
    private val repository: PetLikeRepository
) : Actor<PetListCommand, PetListEvent> {
    override fun process(commands: Flow<PetListCommand>): Flow<PetListEvent> {
        return commands.filterIsInstance<PetListCommand.ChangeLikeStatus>()
            .map { repository.changeLikeStatus(it.id, it.like) }
            .flatMapMerge { emptyFlow() }
    }
}
