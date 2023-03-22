package ru.gortea.petter.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.mapContent

open class MapSourceRepository<T, R>(
    source: suspend (Arguments) -> T,
    private val mapper: suspend (T) -> R,
    private val onFailed: suspend (Throwable) -> Unit = {  },
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Repository<R> {

    private val sourceRepo = SourceRepository(
        source = source,
        coroutineScope = coroutineScope
    )

    override fun invalidate(args: Arguments) {
        sourceRepo.invalidate(args)
    }

    override suspend fun get(): Flow<DataState<R>> {
        return sourceRepo.get()
            .onEach { if (it is DataState.Fail) onFailed(it.reason) }
            .map { it.mapContent(mapper) }
    }
}
