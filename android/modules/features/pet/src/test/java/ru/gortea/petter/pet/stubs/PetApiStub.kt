package ru.gortea.petter.pet.stubs

import kotlinx.coroutines.delay
import okhttp3.MultipartBody
import ru.gortea.petter.pet.data.api.PetApi
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.Species
import java.time.LocalDate

internal class PetApiStub : PetApi {

    override suspend fun getPetById(id: String): PetFullModel {
        delay(100)
        return PetFullModel(
            id = id,
            ownerId = "",
            name = "",
            price = -1,
            species = Species.CAT,
            breed = "",
            gender = Gender.FEMALE,
            birthDate = LocalDate.of(2022, 1, 1)
        )
    }

    override suspend fun uploadAvatar(id: String, image: MultipartBody.Part) = delay(100)

    override suspend fun updatePet(id: String, model: PetFullModel) = delay(100)

    override suspend fun createPet(model: PetFullModel): PetFullModel {
        delay(100)
        return model.copy(id = "id")
    }

    override suspend fun deletePet(id: String) = delay(100)

    override suspend fun deletePhoto(folder: String, file: String) = delay(100)

    override suspend fun changeLikeStatus(id: String, liked: Boolean) = delay(100)
}
