package ru.gortea.petter.pet.list.data

import ru.gortea.petter.data.paging.SourcePagingRepository
import ru.gortea.petter.pet.list.data.api.PetListApiService
import ru.gortea.petter.pet.list.data.model.PetListItemModel
import ru.gortea.petter.pet.list.data.model.PetListKey
import ru.gortea.petter.pet.list.data.model.PetListPageState
import ru.gortea.petter.profile.data.local.UserLocalRepository

class PetListRepository(
    private val api: PetListApiService,
    private val userRepository: UserLocalRepository
) : SourcePagingRepository<PetListPageState, PetListItemModel>(
    initialState = PetListPageState(),
    invalidatePageMapper = { key -> key.copy(page = 0) },
    nextPageMapper = { key -> key.copy(page = key.page + 1) },
    source = { state ->
        val key = state.petListKey
        val excludeOwnerId = if (key.excludeMe) userRepository.getCurrentUser().id else null

        api.getPetList(
            favourites = key.favourites,
            page = state.page,
            pageSize = state.pageSize,
            state = key.state,
            ownerId = key.ownerId,
            excludeOwnerId = excludeOwnerId,
            species = key.species,
            breed = key.breed,
            gender = key.gender,
            minPrice = key.minPrice,
            maxPrice = key.maxPrice
        )
    }
) {
    fun invalidate(args: PetListKey, refresh: Boolean = false) {
        val pageState = PetListPageState(petListKey = args, pageSize = args.pageSize)

        super.invalidate(pageState, refresh)
    }
}
