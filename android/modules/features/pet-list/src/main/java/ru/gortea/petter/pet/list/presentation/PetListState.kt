package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.pet.list.data.model.PetListItemModel

internal data class PetListState(
    val currentUserId: String? = null,
    val pageSize: Int = 30,
    val dataState: PagingDataState<PetListItemModel> = PagingDataState.Initial.Empty
)
