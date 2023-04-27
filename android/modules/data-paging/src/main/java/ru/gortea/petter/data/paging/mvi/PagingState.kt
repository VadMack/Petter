package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.data.paging.model.RequestState

internal data class PagingState<T : RequestState>(
    val pageState: T,
    val lastPage: Boolean = false,
    val dataState: PagingDataState<*> = PagingDataState.Initial.Empty
)
