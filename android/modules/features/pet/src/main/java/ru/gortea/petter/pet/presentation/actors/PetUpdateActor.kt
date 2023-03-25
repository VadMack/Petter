package ru.gortea.petter.pet.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.data.CreatePetRepository
import ru.gortea.petter.pet.data.UpdatePetRepository
import ru.gortea.petter.pet.data.model.PetUpdateModel
import ru.gortea.petter.pet.presentation.PetCommand
import ru.gortea.petter.pet.presentation.PetEvent
import ru.gortea.petter.pet.presentation.state.toPetFullModel

internal class PetUpdateActor(
    private val createRepository: CreatePetRepository,
    private val updateRepository: UpdatePetRepository
) : Actor<PetCommand, PetEvent> {

    override fun process(commands: Flow<PetCommand>): Flow<PetEvent> {
        return commands.filterIsInstance<PetCommand.UpdatePet>()
            .mapLatest {
                val updateModel = PetUpdateModel(it.model.photoPath?.toString(), it.model.toPetFullModel())
                if (it.model.model == null) {
                    createRepository.invalidate(updateModel)
                } else {
                    updateRepository.invalidate(updateModel)
                }
            }
            .flatMapMerge { emptyFlow() }
    }
}
