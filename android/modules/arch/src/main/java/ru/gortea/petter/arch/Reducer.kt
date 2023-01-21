package ru.gortea.petter.arch

import ru.gortea.petter.arch.model.Message
import ru.gortea.petter.arch.model.MessageBuilder

abstract class Reducer<State : Any, Event : Any, Action : Any, Operation : Any> {

    protected abstract fun MessageBuilder<State, Action, Operation>.reduce(event: Event)

    internal fun reduce(state: State, event: Event): Message<State, Operation, Action> {
        val messageBuilder = MessageBuilder<State, Action, Operation>(state)
        messageBuilder.reduce(event)
        return messageBuilder.build()
    }
}
