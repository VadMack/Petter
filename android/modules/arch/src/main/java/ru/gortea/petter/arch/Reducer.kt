package ru.gortea.petter.arch

import ru.gortea.petter.arch.model.Message
import ru.gortea.petter.arch.model.MessageBuilder

abstract class Reducer<State : Any, in Event : Any, Command : Any> {

    protected abstract fun MessageBuilder<State, Command>.reduce(event: Event)

    internal fun reduce(state: State, event: Event): Message<State, Command> {
        val messageBuilder = MessageBuilder<State, Command>(state)
        messageBuilder.reduce(event)
        return messageBuilder.build()
    }
}
