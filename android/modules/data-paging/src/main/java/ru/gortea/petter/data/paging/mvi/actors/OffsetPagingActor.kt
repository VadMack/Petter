package ru.gortea.petter.data.paging.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.data.paging.model.OffsetState
import ru.gortea.petter.data.paging.mvi.PagingCommand
import ru.gortea.petter.data.paging.mvi.PagingEvent

internal class OffsetPagingActor<A : OffsetState, T>(
    private val source: suspend (A) -> List<T>
) : Actor<PagingCommand, PagingEvent> {

    override fun process(commands: Flow<PagingCommand>): Flow<PagingEvent> {
        return commands.filterIsInstance<PagingCommand.LoadPageByOffset<A>>()
            .mapLatest {
                try {
                    PagingEvent.Internal.LoadingComplete(source(it.args))
                } catch (t: Throwable) {
                    PagingEvent.Internal.LoadingFailed(t)
                }
            }
    }
}
