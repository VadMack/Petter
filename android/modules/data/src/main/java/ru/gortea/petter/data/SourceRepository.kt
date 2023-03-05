package ru.gortea.petter.data

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.gortea.petter.arch.collection.collect
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.mvi.RepositoryEvent
import ru.gortea.petter.data.mvi.RepositoryState
import ru.gortea.petter.data.mvi.RepositoryStore

@Suppress("UNCHECKED_CAST")
class SourceRepository<T>(
    source: suspend (Arguments) -> T
) : Repository<T> {

    private val store = RepositoryStore(source)
    private val dataFlow = MutableSharedFlow<DataState<T>>(extraBufferCapacity = 5)

    init {
        store.collect(::stateMapper, ::stateRenderer)
    }

    private fun stateRenderer(state: DataState<T>) {
        dataFlow.tryEmit(state)
    }

    private fun stateMapper(state: RepositoryState): DataState<T> = state.dataState as DataState<T>

    override fun invalidate(args: Arguments) {
        store.dispatch(RepositoryEvent.User.Invalidate(args))
    }

    override suspend fun get(args: Arguments): Flow<DataState<T>> {
        coroutineScope { store.attach(this) }
        store.dispatch(RepositoryEvent.User.Invalidate(args))

        return dataFlow.asSharedFlow()
    }
}
