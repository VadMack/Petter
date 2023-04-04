package ru.gortea.petter.pet.list.data.model

import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species

data class PetListKey(
    val favourites: Boolean = false,
    val excludeMe: Boolean = false,
    val state: PetCardState? = null,
    val ownerId: String? = null,
    val species: Species? = null,
    val breed: String? = null,
    val gender: Gender? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val pageSize: Int = 30
)
