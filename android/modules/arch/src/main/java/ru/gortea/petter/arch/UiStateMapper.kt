package ru.gortea.petter.arch

fun interface UiStateMapper<in State : Any, out UiState : Any> {
    fun map(state: State): UiState
}
