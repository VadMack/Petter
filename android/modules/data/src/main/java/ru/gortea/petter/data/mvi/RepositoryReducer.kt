package ru.gortea.petter.data.mvi

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.mvi.RepositoryCommand as Command
import ru.gortea.petter.data.mvi.RepositoryEvent as Event
import ru.gortea.petter.data.mvi.RepositoryState as State

internal class RepositoryReducer : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.Internal -> handleInternalEvent(event)
            is Event.User -> handleUserEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleInternalEvent(event: Event.Internal) {
        when(event) {
            is Event.Internal.LoadingComplete<*> -> state {
                copy(dataState = DataState.Content(event.data))
            }
            is Event.Internal.LoadingFailed -> state {
                copy(dataState = DataState.Fail(event.reason))
            }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUserEvent(event: Event.User) {
        when(event) {
            is Event.User.Invalidate -> {
                commands(Command.Invalidate(event.args))
                state {
                    val newState = when(val oldState = state.dataState) {
                        is DataState.Fail -> DataState.Loading.WithError(oldState.reason)
                        is DataState.Content<*> -> DataState.Loading.WithContent(oldState.content)
                        else -> DataState.Loading()
                    }

                    copy(dataState = newState)
                }
            }
        }
    }
}
