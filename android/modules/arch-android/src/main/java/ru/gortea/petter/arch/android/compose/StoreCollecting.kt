package ru.gortea.petter.arch.android.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.store.MviStore

@SuppressLint("ComposableNaming")
@Composable
inline fun <State : Any, UiState : Any, Action : Any> MviStore<State, *, Action>.collect(
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: @Composable (UiState) -> Unit
) {
    val state by stateFlow.collectAsState()
    stateRender(stateMapper.map(state))
}

@SuppressLint("ComposableNaming")
@Composable
inline fun <State : Any, UiState : Any, Action : Any> MviStore<State, *, Action>.collect(
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: @Composable (UiState) -> Unit,
    actionHandler: @Composable (Action) -> Unit
) {
    val state by stateFlow.collectAsState()
    val action by actionsFlow.collectAsState(initial = null)
    stateRender(stateMapper.map(state))
    action?.let { actionHandler(it) }
}