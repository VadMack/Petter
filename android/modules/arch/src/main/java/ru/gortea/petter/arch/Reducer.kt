package ru.gortea.petter.arch

import ru.gortea.petter.arch.model.Message
import ru.gortea.petter.arch.model.MessageBuilder

abstract class Reducer<State : Any, in Event : Any, Action : Any, Command : Any> {

    protected abstract fun MessageBuilder<State, Action, Command>.reduce(event: Event)

    internal fun reduce(state: State, event: Event): Message<State, Command, Action> {
        val messageBuilder = MessageBuilder<State, Action, Command>(state)
        messageBuilder.reduce(event)
        return messageBuilder.build()
    }
}
