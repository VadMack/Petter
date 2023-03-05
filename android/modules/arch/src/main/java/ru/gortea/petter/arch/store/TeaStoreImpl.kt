package ru.gortea.petter.arch.store

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer

internal class TeaStoreImpl<State : Any, Event : Any, Action : Any, Command : Any>(
    initialState: State,
    private val reducer: Reducer<State, Event, Action, Command>,
    private val actors: List<Actor<Command, Event>> = listOf()
) : MviStore<State, Event, Action>(initialState) {

    private val commandsFlow = MutableSharedFlow<Command>(replay = 1)

    override fun attach(coroutineScope: CoroutineScope) {
        super.attach(coroutineScope)
        start()
    }
    private fun start() {
        startActors()
        startEventsFlow()
        startCommandsFlow()
    }

    private fun startCommandsFlow() {
        coroutineScope.launch {
            commandsFlow.shareIn(this + Dispatchers.Unconfined, SharingStarted.Eagerly)
        }
    }

    private fun startEventsFlow() {
        coroutineScope.launch {
            eventsFlow
                .map { reducer.reduce(stateFlow.value, it) }
                .onEach { message -> _stateFlow.emit(message.state) }
                .onEach { message -> message.commands.forEach { commandsFlow.emit(it) } }
                .onEach { message -> message.actions.forEach { _actionsChannel.send(it) } }
                .launchIn(this + Dispatchers.Unconfined)
        }
    }

    private fun startActors() {
        actors.forEach { actor ->
            coroutineScope.launch {
                actor.process(commandsFlow)
                    .onEach(_eventsChannel::send)
                    .launchIn(this + Dispatchers.Unconfined)
            }
        }
    }
}
