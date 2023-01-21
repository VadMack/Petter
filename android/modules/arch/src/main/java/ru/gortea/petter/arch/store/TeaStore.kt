package ru.gortea.petter.arch.store

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer

class TeaStore<State : Any, Event : Any, Action : Any, Operation : Any> internal constructor(
    initialState: State,
    private val reducer: Reducer<State, Event, Action, Operation>,
    private val actors: () -> List<Actor<Operation, Event>> = { listOf() }
) : MviStore<State, Event, Action>(initialState) {

    private val commandsFlow = MutableSharedFlow<Operation>(replay = 1)

    init {
        start()
    }

    private fun start() {
        startActors()
        startEventsFlow()
        startOperationsFlow()
    }

    private fun startOperationsFlow() {
        commandsFlow.shareIn(coroutineScope + Dispatchers.Unconfined, SharingStarted.Eagerly)
    }

    private fun startEventsFlow() {
        eventsFlow
            .map { reducer.reduce(stateFlow.value, it) }
            .onEach { message -> stateFlow.emit(message.state) }
            .onEach { message -> message.commands.forEach { commandsFlow.emit(it) } }
            .onEach { message -> message.actions.forEach { actionsFlow.emit(it) } }
            .launchIn(coroutineScope + Dispatchers.Unconfined)
    }

    private fun startActors() {
        actors().forEach { actor ->
            coroutineScope.launch(Dispatchers.Unconfined) {
                actor.process(commandsFlow)
                    .onEach(eventsFlow::emit)
                    .launchIn(this)
            }
        }
    }
}
