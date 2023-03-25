package ru.gortea.petter.pet.data

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetUpdateModel

class UpdatePetRepository(
    private val api: PetApi
) : SourceRepository<Unit>(
    source = {
        val updateModel = it as PetUpdateModel
        api.updatePet(updateModel.id, updateModel.model)
    }
)
