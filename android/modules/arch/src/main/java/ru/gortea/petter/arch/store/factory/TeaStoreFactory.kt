package ru.gortea.petter.arch.store.factory

import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.TeaStore

fun <State : Any, Event : Any, Action : Any, Operation : Any> TeaStore(
    initialState: State,
    reducer: Reducer<State, Event, Action, Operation>,
    actors: () -> List<Actor<Operation, Event>> = { listOf() }
): MviStore<State, Event, Action> = TeaStore(initialState, reducer, actors)
