package ru.gortea.petter.pet.data.model

import ru.gortea.petter.data.model.Arguments

data class PetUpdateModel(
    val id: String,
    val model: PetFullModel
) : Arguments
