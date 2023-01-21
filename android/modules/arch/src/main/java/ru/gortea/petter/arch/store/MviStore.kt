package ru.gortea.petter.arch.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gortea.petter.arch.UiStateMapper

abstract class MviStore<State: Any, Event : Any, Action: Any>(initialState: State) : Store<Event> {
    private var _coroutineScope: CoroutineScope? = null
    protected val coroutineScope: CoroutineScope
        get() = _coroutineScope ?: error("Store is not attached")

    internal val stateFlow = MutableStateFlow(initialState)
    internal val eventsFlow = MutableSharedFlow<Event>(replay = 1)
    internal val actionsFlow = MutableSharedFlow<Action>(replay = 1)

    override fun dispatch(event: Event) {
        coroutineScope.launch { eventsFlow.emit(event) }
    }

    override fun attach(coroutineScope: CoroutineScope) {
        if (_coroutineScope != null) error("Store has to be attached once")

        _coroutineScope = coroutineScope
    }
}

fun<State : Any, Event : Any> MviStore<State, Event, *>.renderState(
    coroutineScope: CoroutineScope,
    renderer: (State) -> Unit
) {
    stateFlow.onEach(renderer)
        .launchIn(coroutineScope)
}

fun <UiState : Any, State : Any, Event : Any> MviStore<State, Event, *>.renderState(
    coroutineScope: CoroutineScope,
    mapper: UiStateMapper<State, UiState>,
    renderer: (UiState) -> Unit
) {
    stateFlow.map(mapper::map)
        .onEach(renderer)
        .launchIn(coroutineScope)
}

fun<Action : Any, State : Any, Event : Any> MviStore<State, Event, Action>.handleAction(
    coroutineScope: CoroutineScope,
    dispatcher: (Action) -> Unit
) {
    actionsFlow.onEach(dispatcher)
        .launchIn(coroutineScope)
}
