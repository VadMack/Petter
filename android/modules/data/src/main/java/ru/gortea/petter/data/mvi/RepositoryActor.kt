package ru.gortea.petter.data.mvi

import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.data.mvi.RepositoryCommand.Invalidate
import ru.gortea.petter.data.mvi.RepositoryEvent.Internal

internal class RepositoryActor<T>(
    private val source: suspend (Arguments) -> T
) : Actor<RepositoryCommand, RepositoryEvent> {

    override fun process(commands: Flow<RepositoryCommand>): Flow<RepositoryEvent> {
        return commands.filterIsInstance<Invalidate>()
            .mapLatest {
                try {
                    Internal.LoadingComplete(source(it.args))
                } catch (t: Throwable) {
                    Internal.LoadingFailed(t)
                }
            }
    }
}
