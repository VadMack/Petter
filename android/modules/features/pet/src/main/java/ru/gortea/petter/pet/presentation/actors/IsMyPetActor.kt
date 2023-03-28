package ru.gortea.petter.pet.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.pet.presentation.PetCommand
import ru.gortea.petter.pet.presentation.PetEvent
import ru.gortea.petter.profile.data.local.UserLocalRepository

internal class IsMyPetActor(
    private val profileRepository: UserLocalRepository
) : Actor<PetCommand, PetEvent> {

    override fun process(commands: Flow<PetCommand>): Flow<PetEvent> {
        return commands.filterIsInstance<PetCommand.IsMyPet>()
            .mapLatest { it.pet == null || it.pet.ownerId == profileRepository.getCurrentUser().id }
            .map { PetEvent.IsMyPet(it) }
    }
}
