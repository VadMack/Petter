package ru.gortea.petter.pet.data

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetIdModel

class DeletePetRepository(
    private val api: PetApi
) : SourceRepository<Unit>(
    source = {
        val model = it as PetIdModel
        api.deletePet(model.id)
    }
)
