package ru.gortea.petter.pet.list.data.model

import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.Species

data class PetListKey(
    val favourites: Boolean = false,
    val ownerId: String? = null,
    val species: Species? = null,
    val breed: String? = null,
    val gender: Gender? = null,
    val pageSize: Int = 30
)
