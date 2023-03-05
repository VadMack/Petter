package ru.gortea.petter.arch.store.factory

import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.TeaStoreImpl

fun <State : Any, Event : Any, Action : Any, Command : Any> TeaStore(
    initialState: State,
    reducer: Reducer<State, Event, Action, Command>,
    actors: List<Actor<Command, Event>> = listOf()
): MviStore<State, Event, Action> = TeaStoreImpl(initialState, reducer, actors)
