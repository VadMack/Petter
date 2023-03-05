package ru.gortea.petter.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import ru.gortea.petter.arch.collection.collect
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.mvi.RepositoryEvent
import ru.gortea.petter.data.mvi.RepositoryState
import ru.gortea.petter.data.mvi.RepositoryStore

@Suppress("UNCHECKED_CAST")
class SourceRepository<T>(
    source: suspend (Arguments) -> T,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Repository<T> {

    private val store = RepositoryStore(source)
    private val dataFlow = MutableSharedFlow<DataState<T>>(extraBufferCapacity = 1)

    init {
        store.attach(coroutineScope)
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
        store.dispatch(RepositoryEvent.User.Invalidate(args))

        return dataFlow.onCompletion { coroutineScope.cancel() }
    }
}
