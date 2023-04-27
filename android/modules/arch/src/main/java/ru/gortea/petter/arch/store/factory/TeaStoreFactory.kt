package ru.gortea.petter.arch.store.factory

import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.TeaStoreImpl

fun <State : Any, Event : Any, Command : Any> TeaStore(
    initialState: State,
    reducer: Reducer<State, Event, Command>,
    actors: List<Actor<Command, Event>> = listOf(),
    initialEvents: List<Event> = listOf()
): MviStore<State, Event> = TeaStoreImpl(initialState, reducer, actors).apply {
    initialEvents.forEach(::dispatch)
}
