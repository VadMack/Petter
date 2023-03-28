package ru.gortea.petter.pet.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.data.UpdatePetRepository
import ru.gortea.petter.pet.presentation.PetCommand
import ru.gortea.petter.pet.presentation.PetEvent

internal class PetUpdateInitActor(
    private val repository: UpdatePetRepository
) : Actor<PetCommand, PetEvent> {

    override fun process(commands: Flow<PetCommand>): Flow<PetEvent> {
        return commands.filterIsInstance<PetCommand.InitPetUpdate>()
            .flatMapLatest { repository.get() }
            .map { PetEvent.UpdatePetStatus(it) }
    }
}
