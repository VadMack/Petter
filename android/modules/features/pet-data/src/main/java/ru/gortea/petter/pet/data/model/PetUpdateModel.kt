package ru.gortea.petter.pet.data.model

import ru.gortea.petter.data.model.Arguments

data class PetUpdateModel(
    val photoPath: String?,
    val model: PetFullModel
) : Arguments
