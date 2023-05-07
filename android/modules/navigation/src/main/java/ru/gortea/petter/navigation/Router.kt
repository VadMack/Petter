package ru.gortea.petter.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Router<in T : NavTarget> : CommandController {
    fun updateRoot(target: T)
    fun navigateTo(target: T)
    fun restoreIfExists(target: T)
    fun pop()

    companion object {
        fun<T: NavTarget> stub(): Router<T> = object : Router<T> {
            override fun updateRoot(target: T) = Unit

            override fun navigateTo(target: T) = Unit

            override fun restoreIfExists(target: T) = Unit

            override fun pop() = Unit

            override fun sendCommand(command: NavCommand) = Unit

            override val commands: StateFlow<NavCommand>
                get() = MutableStateFlow(NavCommand.Empty)
        }

    }
}
