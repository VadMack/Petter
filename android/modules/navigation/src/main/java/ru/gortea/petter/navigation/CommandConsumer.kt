package ru.gortea.petter.navigation

import kotlinx.coroutines.flow.StateFlow

interface CommandConsumer {
    val commands: StateFlow<NavCommand>
    fun setCommandsConsumer(consumer: (NavCommand) -> Unit) = Unit
}
