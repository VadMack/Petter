package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessageState
import ru.gortea.petter.chat.data.mvi.ChatDataEvent.Internal
import ru.gortea.petter.chat.data.mvi.ChatDataEvent.User
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ru.gortea.petter.chat.data.mvi.ChatDataCommand as Command
import ru.gortea.petter.chat.data.mvi.ChatDataEvent as Event
import ru.gortea.petter.chat.data.mvi.ChatDataState as State

internal class ChatDataReducer : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when (event) {
            is Internal -> handleInternalEvent(event)
            is User -> handleUserEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.handleInternalEvent(event: Internal) {
        when (event) {
            is Internal.InitApi -> commands(
                Command.InitMessagesLoad,
                Command.SubscribeToMessages,
                Command.SubscribeToLifecycle
            )
            is Internal.ConnectionClosed -> state { copy(connectionClosed = true) }
            is Internal.MessagesLoadStatus -> state { copy(storedMessages = event.state) }
            is Internal.MessageSent -> state { messageSent(event.state) }
            is Internal.MessageReceived -> messageReceived(event.state)
            is Internal.ChatLifecycle -> chatLifecycle(event.event)
        }
    }

    private fun State.messageSent(messageState: SentMessageState): State {
        val messages = sentMessages.toMutableList()

        when (messageState) {
            is SentMessageState.Success -> Unit
            is SentMessageState.Loading -> messages.add(0, messageState)
            is SentMessageState.Fail -> {
                messages.replaceAll { if (it.message == messageState.message) messageState else it }
            }
        }

        return copy(sentMessages = messages)
    }

    private fun MessageBuilder<State, Command>.messageReceived(
        messageState: ServerMessageState
    ) = state {
        when (messageState) {
            is ServerMessageState.Content -> {
                val messages = receivedMessages.toMutableList()
                val sentMessages = sentMessages.toMutableList()
                messages.add(0, messageState.content)

                if (messageState.content.senderId == senderId && sentMessages.isNotEmpty()) {
                    sentMessages.removeFirst()
                }

                copy(receivedMessages = messages, sentMessages = sentMessages)
            }
            is ServerMessageState.Fail -> {
                commands(Command.CloseConnection)
                this
            }
        }
    }

    private fun MessageBuilder<State, Command>.chatLifecycle(
        event: LifecycleEvent
    ) = state {
        var connectionClosed = false
        when(event.type) {
            LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> commands(Command.CloseConnection)
            LifecycleEvent.Type.CLOSED -> connectionClosed = true
            LifecycleEvent.Type.ERROR -> commands(Command.CloseConnection)
            LifecycleEvent.Type.OPENED, null -> Unit
        }
        copy(lifecycleEvent = event, connectionClosed = connectionClosed)
    }

    private fun MessageBuilder<State, Command>.handleUserEvent(event: User) {
        when(event) {
            is User.LoadPage -> commands(Command.LoadPage(state.messagesCount))
            is User.SendMessage -> commands(Command.SendMessage(event.message))
            is User.CloseConnection -> commands(Command.CloseConnection)
        }
    }
}
