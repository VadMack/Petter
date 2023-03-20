package ru.gortea.petter.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.mapContent

open class MapSourceRepository<T, R>(
    source: suspend (Arguments) -> T,
    private val mapper: suspend (T) -> R,
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
        return sourceRepo.get().map { it.mapContent(mapper) }
    }
}
