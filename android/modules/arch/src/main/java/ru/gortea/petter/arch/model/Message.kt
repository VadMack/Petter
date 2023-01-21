package ru.gortea.petter.arch.model

internal data class Message<State : Any, Command : Any, Action : Any>(
    val state: State,
    val commands: List<Command> = emptyList(),
    val actions: List<Action> = emptyList()
)
