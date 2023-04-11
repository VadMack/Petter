package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessageState
import ru.gortea.petter.chat.data.mvi.ChatDataEvent.Internal
import ru.gortea.petter.chat.data.mvi.ChatDataEvent.User
import ru.gortea.petter.chat.data.mvi.ChatDataCommand as Command
import ru.gortea.petter.chat.data.mvi.ChatDataEvent as Event
import ru.gortea.petter.chat.data.mvi.ChatDataState as State

internal class ChatDataReducer : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Internal -> handleInternalEvent(event)
            is User -> handleUserEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleInternalEvent(event: Internal) {
        when (event) {
            is Internal.InitApi -> commands(
                Command.InitMessagesLoad,
                Command.SubscribeToMessages
            )
            is Internal.ConnectionClosed -> state { copy(connectionClosed = true) }
            is Internal.MessagesLoadStatus -> state { copy(storedMessages = event.state) }
            is Internal.MessageSent -> state { messageSent(event.state) }
            is Internal.MessageReceived -> state { messageReceived(event.state) }
        }
    }

    private fun State.messageSent(messageState: SentMessageState): State {
        val messages = sentMessages.toMutableList()

        when (messageState) {
            is SentMessageState.Success -> {
                messages.removeIf { it.message == messageState.message }
            }
            is SentMessageState.Loading -> {
                messages.add(messageState)
            }
            is SentMessageState.Fail -> {
                messages.replaceAll { if (it.message == messageState.message) messageState else it }
            }
        }

        return copy(sentMessages = messages)
    }

    private fun State.messageReceived(messageState: ServerMessageState): State {
        return if (messageState is ServerMessageState.Content) {
            val messages = receivedMessages.toMutableList()
            messages.add(messageState.content)
            copy(receivedMessages = messages)
        } else {
            this
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUserEvent(event: User) {
        when(event) {
            is User.LoadPage -> commands(Command.LoadPage)
            is User.SendMessage -> commands(Command.SendMessage(event.message))
            is User.CloseConnection -> commands(Command.CloseConnection)
        }
    }
}
