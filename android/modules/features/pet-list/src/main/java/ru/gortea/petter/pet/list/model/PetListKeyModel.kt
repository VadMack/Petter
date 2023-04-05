package ru.gortea.petter.pet.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.list.data.model.PetListKey

@Parcelize
data class PetListKeyModel(
    val favourites: Boolean = false,
    val excludeMe: Boolean = false,
    val ownerId: String? = null,
    val state: PetCardState? = null,
    val species: Species? = null,
    val breed: String? = null,
    val gender: Gender? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val pageSize: Int = 30
) : Parcelable

fun PetListKeyModel.toDataKey(): PetListKey {
    return PetListKey(
        favourites = favourites,
        excludeMe = excludeMe,
        ownerId = ownerId,
        state = state,
        species = species,
        breed = breed,
        gender = gender,
        minPrice = minPrice,
        maxPrice = maxPrice,
        pageSize = pageSize
    )
}

fun PetListKeyModel.isEmpty(): Boolean {
    return listOfNotNull(species, breed, gender, minPrice, maxPrice).isEmpty()
}
