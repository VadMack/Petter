package ru.gortea.petter.pet.data

import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.PetIdModel

class GetPetRepository(
    private val api: PetApi
) : SourceRepository<PetFullModel>(
    source = {
        val id = (it as PetIdModel).id
        api.getPetById(id)
    }
)
