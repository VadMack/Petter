package ru.gortea.petter.arch.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gortea.petter.arch.UiStateMapper

abstract class MviStore<State: Any, Event : Any, Action: Any>(initialState: State) : Store<Event> {
    private var _coroutineScope: CoroutineScope? = null
    internal val coroutineScope: CoroutineScope
        get() = _coroutineScope ?: error("Store is not attached")

    protected val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    protected val _eventsChannel = Channel<Event>(Channel.BUFFERED)
    internal val eventsFlow = _eventsChannel.consumeAsFlow()

    protected val _actionsChannel = Channel<Action>(Channel.BUFFERED)
    val actionsFlow = _actionsChannel.consumeAsFlow()

    override fun dispatch(event: Event) {
        coroutineScope.launch { _eventsChannel.send(event) }
    }

    override fun attach(coroutineScope: CoroutineScope) {
        if (_coroutineScope != null) error("Store has to be attached once")

        _coroutineScope = coroutineScope
    }
}

internal fun<State : Any, Event : Any> MviStore<State, Event, *>.renderState(
    coroutineScope: CoroutineScope,
    renderer: (State) -> Unit
) {
    stateFlow.onEach(renderer)
        .launchIn(coroutineScope)
}

internal fun <UiState : Any, State : Any, Event : Any> MviStore<State, Event, *>.renderState(
    coroutineScope: CoroutineScope,
    mapper: UiStateMapper<State, UiState>,
    renderer: (UiState) -> Unit
) {
    stateFlow.map(mapper::map)
        .onEach(renderer)
        .launchIn(coroutineScope)
}

internal fun<Action : Any, State : Any, Event : Any> MviStore<State, Event, Action>.handleAction(
    coroutineScope: CoroutineScope,
    dispatcher: (Action) -> Unit
) {
    actionsFlow.onEach(dispatcher)
        .launchIn(coroutineScope)
}
