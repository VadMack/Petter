package ru.gortea.petter.data.paging

import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.data.paging.model.PageState
import ru.gortea.petter.data.paging.model.PagingDataState

interface PagingRepository<S : PageState, T> {
    fun invalidate(args: S, refresh: Boolean = false)
    fun loadPage()
    fun get(): Flow<PagingDataState<T>>
}
