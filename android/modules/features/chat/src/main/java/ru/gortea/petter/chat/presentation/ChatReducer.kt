package ru.gortea.petter.chat.presentation

import ru.gortea.chat.data.messages.model.MessageModel
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.android.util.isNotBlank
import ru.gortea.petter.arch.android.util.text
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.chat.navigation.ChatNavTarget
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.chat.presentation.ChatCommand as Command
import ru.gortea.petter.chat.presentation.ChatEvent as Event
import ru.gortea.petter.chat.presentation.ChatRoomCreationEvent as RoomCreationEvent
import ru.gortea.petter.chat.presentation.ChatState as State
import ru.gortea.petter.chat.presentation.ChatUiEvent as UiEvent

internal class ChatReducer(
    private val router: PetterRouter<ChatNavTarget>
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when(event) {
            is Event.MessagesStatus -> messagesStatus(event.state)
            is Event.InitApi -> commands(Command.InitChatRoomCreation, Command.InitMessages)
            is RoomCreationEvent -> roomCreationEvent(event)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.messagesStatus(
        state: PagingDataState<MessageModel>
    ) {
        state {
            if (this is State.ContentChatState) {
                copy(messages = state)
            } else {
                this
            }
        }
    }

    private fun MessageBuilder<State, Command>.roomCreationEvent(event: RoomCreationEvent) {
        when(event) {
            is RoomCreationEvent.CreateChatRoom -> commands(Command.CreateChatRoomId(event.userId))
            is RoomCreationEvent.ChatRoomCreationStatus -> chatRoomCreationStatus(event.state)
        }
    }

    private fun MessageBuilder<State, Command>.chatRoomCreationStatus(
        state: DataState<ChatRoomModel>
    ) {
        state {
            when(this) {
                is State.RoomCreationChatState -> roomCreationChatState(state)
                is State.ContentChatState -> error("Illegal state for room creation: $state")
            }
        }
    }

    private fun State.RoomCreationChatState.roomCreationChatState(
        state: DataState<ChatRoomModel>
    ): State {
        return copy(room = state)
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.LoadPage -> commands(Command.LoadPage)
            is UiEvent.SendMessage -> sendMessage()
            is UiEvent.MessageFieldChanged -> messageFieldChanged(event.text)
            is UiEvent.Back -> router.pop()
            is UiEvent.OpenCompanionProfile -> router.navigateTo(ChatNavTarget.Profile)
        }
    }

    private fun MessageBuilder<State, Command>.sendMessage() {
        state {
            when (this) {
                is State.ContentChatState -> {
                    if (messageField.isNotBlank()) {
                        commands(Command.SendMessage(messageField.text.trim()))
                    }

                    copy(messageField = messageField.text(""))
                }
                is State.RoomCreationChatState -> error("Illegal state for send message: $state")
            }
        }
    }

    private fun MessageBuilder<State, Command>.messageFieldChanged(text: String) {
        state {
            when (this) {
                is State.ContentChatState -> copy(messageField = messageField.text(text))
                is State.RoomCreationChatState -> error("Illegal state for edit message: $state")
            }
        }
    }
}
