package ru.gortea.petter.pet.list.stubs

import kotlinx.coroutines.delay
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.list.data.api.PetListApi
import ru.gortea.petter.pet.list.data.model.PetListItemModel
import java.time.LocalDate

internal object PetListApiStub : PetListApi {
    override suspend fun getPetList(
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
        delay(500)
        return listOf(
            PetListItemModel(
                id = "id",
                ownerId = "ownerId",
                name = "",
                species = Species.CAT,
                price = -1,
                breed = "",
                gender = Gender.FEMALE,
                birthDate = LocalDate.of(2023, 1, 1),
                hasAchievements = false,
                imagePaths = null,
                state = PetCardState.OPEN,
                liked = false
            )
        )
    }

    override suspend fun getFavourites(): List<PetListItemModel> {
        delay(500)
        return emptyList()
    }
}
