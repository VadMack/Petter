package ru.gortea.petter.data.paging.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.data.paging.model.PageState

internal class PagingActor<A: PageState, T>(
    private val source: suspend (A) -> List<T>
) : Actor<PagingCommand, PagingEvent> {

    override fun process(commands: Flow<PagingCommand>): Flow<PagingEvent> {
        return commands.filterIsInstance<PagingCommand.LoadPage<A>>()
            .mapLatest {
                try {
                    PagingEvent.Internal.LoadingComplete(source(it.args))
                } catch (t: Throwable) {
                    PagingEvent.Internal.LoadingFailed(t)
                }
            }
    }
}
