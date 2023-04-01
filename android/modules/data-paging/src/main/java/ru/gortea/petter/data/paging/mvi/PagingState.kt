package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.data.paging.model.PageState
import ru.gortea.petter.data.paging.model.PagingDataState

internal data class PagingState<T : PageState>(
    val pageState: T,
    val lastPage: Boolean = false,
    val dataState: PagingDataState<*> = PagingDataState.Initial.Empty
)
