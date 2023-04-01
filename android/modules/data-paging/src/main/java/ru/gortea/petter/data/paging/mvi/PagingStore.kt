package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.data.paging.model.PageState

internal typealias PagingStore<T> = MviStore<PagingState<T>, PagingEvent, Nothing>

internal fun <A: PageState, T> createPagingStore(
    initialState: A,
    invalidatePageMapper: (A) -> A,
    nextPageMapper: (A) -> A,
    source: suspend (A) -> List<T>
): PagingStore<A> = TeaStore(
    initialState = PagingState(initialState),
    reducer = PagingReducer(invalidatePageMapper, nextPageMapper),
    actors = listOf(PagingActor(source))
)
