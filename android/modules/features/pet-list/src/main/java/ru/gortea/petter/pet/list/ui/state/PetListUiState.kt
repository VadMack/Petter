package ru.gortea.petter.pet.list.ui.state

import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.pet.list.ui.state.model.PetListItem

internal data class PetListUiState(
    val offset: Int,
    val dataState: PagingDataState<PetListItem>
)
