package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.data.paging.model.OffsetState
import ru.gortea.petter.data.paging.model.PageState
import ru.gortea.petter.data.paging.mvi.actors.OffsetPagingActor
import ru.gortea.petter.data.paging.mvi.actors.PagingActor

internal typealias PagingStore<T> = MviStore<PagingState<T>, PagingEvent>

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

internal fun <A: OffsetState, T> createPagingStore(
    initialState: A,
    invalidatePageMapper: (A) -> A,
    offsetUpdater: (Int, A) -> A,
    source: suspend (A) -> List<T>
): PagingStore<A> = TeaStore(
    initialState = PagingState(initialState),
    reducer = OffsetPagingReducer(invalidatePageMapper, offsetUpdater),
    actors = listOf(OffsetPagingActor(source))
)
