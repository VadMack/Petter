package ru.gortea.petter.pet.list.data

import kotlinx.coroutines.delay
import ru.gortea.petter.data.paging.SourcePagingRepository
import ru.gortea.petter.pet.list.data.api.PetListApiService
import ru.gortea.petter.pet.list.data.model.PetListItemModel
import ru.gortea.petter.pet.list.data.model.PetListKey
import ru.gortea.petter.pet.list.data.model.PetListPageState

class PetListRepository(
    private val api: PetListApiService
) : SourcePagingRepository<PetListPageState, PetListItemModel>(
    initialState = PetListPageState(),
    invalidatePageMapper = { key -> key.copy(page = 0) },
    nextPageMapper = { key -> key.copy(page = key.page + 1) },
    source = { state ->
        val key = state.petListKey
        delay(1000)
        api.getPetList(
            favourites = key.favourites,
            page = state.page,
            pageSize = state.pageSize,
            ownerId = key.ownerId,
            species = key.species,
            breed = key.breed,
            gender = key.gender
        )
    }
) {
    fun invalidate(args: PetListKey) {
        val pageState = PetListPageState(petListKey = args, pageSize = args.pageSize)

        super.invalidate(pageState)
    }
}
