package ru.gortea.petter.arch.collection

import kotlinx.coroutines.CoroutineScope
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.renderState

fun <State : Any, UiState : Any, Action : Any> MviStore<State, *, Action>.collect(
    coroutineScope: CoroutineScope,
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: (UiState) -> Unit
) = renderState(coroutineScope, stateMapper, stateRender)
