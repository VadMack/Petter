package ru.gortea.petter.arch.model

internal data class Message<State : Any, Command : Any>(
    val state: State,
    val commands: List<Command> = emptyList()
)
