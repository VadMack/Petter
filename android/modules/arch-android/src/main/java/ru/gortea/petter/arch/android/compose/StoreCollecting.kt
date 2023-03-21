package ru.gortea.petter.arch.android.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.store.MviStore

@SuppressLint("ComposableNaming")
@Composable
inline fun <State : Any, UiState : Any, Action : Any> MviStore<State, *, Action>.collect(
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: @Composable (UiState) -> Unit
) {
    val state by stateFlow.collectAsState()
    val mapped = remember { stateMapper.map(state) }
    stateRender(mapped)
}

@SuppressLint("ComposableNaming", "FlowOperatorInvokedInComposition")
@Composable
inline fun <State : Any, UiState : Any, Action : Any> MviStore<State, *, Action>.collect(
    stateMapper: UiStateMapper<State, UiState>,
    stateRender: @Composable (UiState) -> Unit,
    crossinline actionHandler: (Action) -> Unit
) {
    LaunchedEffect("Collect") {
        actionsFlow
            .onEach { actionHandler(it) }
            .collect()
    }

    val state by stateFlow.collectAsState()
    val mapped = remember { stateMapper.map(state) }
    stateRender(mapped)
}
