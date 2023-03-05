package ru.gortea.petter.data

import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.model.DataState

interface Repository<T> {
    fun invalidate(args: Arguments = Arguments.empty)
    suspend fun get(args: Arguments = Arguments.empty): Flow<DataState<T>>
}
