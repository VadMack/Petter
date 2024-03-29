package ru.gortea.petter.pet.list.data.api

import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.list.data.model.PetListItemModel

class PetListApiService(
    private val api: PetListApi
) {

    suspend fun getPetList(
        favourites: Boolean,
        page: Int,
        pageSize: Int,
        state: PetCardState?,
        ownerId: String?,
        species: Species?,
        breed: String?,
        gender: Gender?,
        minPrice: Int?,
        maxPrice: Int?,
        excludeOwnerId: String?
    ): List<PetListItemModel> {
        return if (favourites) {
            api.getFavourites()
        } else {
            api.getPetList(
                page = page,
                pageSize = pageSize,
                ownerId = ownerId,
                state = state,
                species = species,
                breed = breed,
                gender = gender,
                minPrice = minPrice,
                maxPrice = maxPrice,
                excludeOwnerId = excludeOwnerId
            )
        }
    }
}
