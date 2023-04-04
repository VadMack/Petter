package ru.gortea.petter.data.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import ru.gortea.petter.arch.collection.collect
import ru.gortea.petter.data.paging.model.PageState
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.data.paging.mvi.PagingEvent
import ru.gortea.petter.data.paging.mvi.PagingState
import ru.gortea.petter.data.paging.mvi.createPagingStore

@Suppress("UNCHECKED_CAST")
open class SourcePagingRepository<S : PageState, T>(
    initialState: S,
    invalidatePageMapper: (S) -> S,
    nextPageMapper: (S) -> S,
    source: suspend (S) -> List<T>,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : PagingRepository<S, T> {

    private val store = createPagingStore(initialState, invalidatePageMapper, nextPageMapper, source)
    private val dataFlow = MutableSharedFlow<PagingDataState<T>>(extraBufferCapacity = 3)

    init {
        store.attach(coroutineScope)
        store.collect(::stateMapper, ::stateRenderer)
    }

    private fun stateRenderer(state: PagingDataState<T>) {
        dataFlow.tryEmit(state)
    }

    private fun stateMapper(state: PagingState<S>) = state.dataState as PagingDataState<T>

    override fun invalidate(args: S, refresh: Boolean) {
        store.dispatch(PagingEvent.User.Invalidate(args, refresh))
    }

    override fun loadPage() {
        store.dispatch(PagingEvent.User.LoadPage)
    }

    override fun get(): Flow<PagingDataState<T>> {
        return dataFlow.onCompletion { coroutineScope.cancel() }
    }
}
