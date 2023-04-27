package ru.gortea.petter.arch.collection

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.renderState

fun <State : Any, UiState : Any> MviStore<State, *>.collect(
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: (UiState) -> Unit
) = renderState(coroutineScope, stateMapper, stateRender)
