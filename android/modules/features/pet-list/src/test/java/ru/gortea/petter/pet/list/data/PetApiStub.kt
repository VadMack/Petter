package ru.gortea.petter.pet.list.data

import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.Species

internal object PetApiStub : PetApi {
    private val petModel = PetFullModel(
        id = "",
        ownerId = "",
        name = "",
        price = -1,
        species = Species.CAT,
        breed = "",
        gender = Gender.FEMALE
    )

    override suspend fun getPetById(id: String): PetFullModel {
        delay(100)
        return petModel
    }

    override suspend fun uploadAvatar(id: String, image: MultipartBody.Part) = delay(100)

    override suspend fun updatePet(id: String, model: PetFullModel) = delay(100)

    override suspend fun createPet(model: PetFullModel): PetFullModel {
        delay(100)
        return petModel
    }

    override suspend fun deletePet(id: String) = delay(100)

    override suspend fun deletePhoto(folder: String, file: String) = delay(100)

    override suspend fun changeLikeStatus(id: String, liked: Boolean) = delay(500)
}
