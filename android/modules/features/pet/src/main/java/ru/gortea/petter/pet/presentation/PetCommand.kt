package ru.gortea.petter.pet.presentation

import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.presentation.state.PetPresentationModel

internal sealed interface PetCommand {
    object InitPetLoad : PetCommand
    object InitPetUpdate : PetCommand
    object InitPetCreate : PetCommand
    object InitPetDelete : PetCommand

    class ChangeLikeStatus(val id: String, val liked: Boolean) : PetCommand
    class IsMyPet(val pet: PetFullModel?) : PetCommand
    class LoadPet(val id: String?) : PetCommand
    class DeletePet(val id: String) : PetCommand
    class UpdatePet(val model: PetPresentationModel) : PetCommand
}
