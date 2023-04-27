package ru.gortea.petter.data.paging

import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.data.paging.model.OffsetState
import ru.gortea.petter.data.paging.model.PagingDataState

interface OffsetPagingRepository<S : OffsetState, T> {
    fun invalidate(args: S, refresh: Boolean = false)
    fun loadPage(offset: Int)
    fun get(): Flow<PagingDataState<T>>
}
