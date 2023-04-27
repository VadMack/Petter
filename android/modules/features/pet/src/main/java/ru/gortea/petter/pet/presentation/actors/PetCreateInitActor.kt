package ru.gortea.petter.pet.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.data.CreatePetRepository
import ru.gortea.petter.pet.presentation.PetCommand
import ru.gortea.petter.pet.presentation.PetEvent

internal class PetCreateInitActor(
    private val repository: CreatePetRepository
) : Actor<PetCommand, PetEvent> {

    override fun process(commands: Flow<PetCommand>): Flow<PetEvent> {
        return commands.filterIsInstance<PetCommand.InitPetCreate>()
            .flatMapLatest { repository.get() }
            .mapLatest { PetEvent.UpdatePetStatus(it) }
    }
}
