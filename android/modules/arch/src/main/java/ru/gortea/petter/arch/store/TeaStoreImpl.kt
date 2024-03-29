package ru.gortea.petter.arch.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.analytics.AnalyticsHandler

internal class TeaStoreImpl<State : Any, Event : Any, Command : Any>(
    initialState: State,
    private val reducer: Reducer<State, Event, Command>,
    private val actors: List<Actor<Command, Event>> = listOf(),
    private val cancellationHandler: CancellationHandler<State>? = null,
    private val analyticsHandler: AnalyticsHandler<State, Event>? = null
) : MviStore<State, Event>(initialState) {

    private val commandsFlow = MutableSharedFlow<Command>(replay = 1)

    override fun attach(coroutineScope: CoroutineScope) {
        super.attach(coroutineScope)
        start()
    }

    override fun cancelled() {
        CoroutineScope(Dispatchers.IO).launch {
            cancellationHandler?.onCancel(stateFlow.value)
        }
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
                .map { event ->
                    val state = stateFlow.value
                    analyticsHandler?.logEvent(state, event)
                    reducer.reduce(state, event)
                }
                .onEach { message -> _stateFlow.emit(message.state) }
                .onEach { message -> message.commands.forEach { commandsFlow.emit(it) } }
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
