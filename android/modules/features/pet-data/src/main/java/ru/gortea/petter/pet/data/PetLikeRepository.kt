package ru.gortea.petter.pet.data

import ru.gortea.petter.pet.data.api.PetApi

class PetLikeRepository(
    private val api: PetApi
) {

    suspend fun changeLikeStatus(id: String, like: Boolean) {
        // TODO Use like
        api.changeLikeStatus(id)
    }
}
