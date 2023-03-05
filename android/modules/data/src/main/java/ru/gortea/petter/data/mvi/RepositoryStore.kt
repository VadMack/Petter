package ru.gortea.petter.data.mvi

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.data.model.Arguments

internal typealias RepositoryStore = MviStore<RepositoryState, RepositoryEvent, Nothing>

internal fun<T> RepositoryStore(
    source: suspend (Arguments) -> T
): RepositoryStore = TeaStore(
    initialState = RepositoryState(),
    reducer = RepositoryReducer(),
    actors = listOf(
        RepositoryActor(source)
    )
)
