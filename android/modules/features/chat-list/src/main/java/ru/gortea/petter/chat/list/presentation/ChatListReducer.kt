package ru.gortea.petter.chat.list.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.list.navigation.ChatListNavTarget
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.chat.list.presentation.ChatListCommand as Command
import ru.gortea.petter.chat.list.presentation.ChatListEvent as Event
import ru.gortea.petter.chat.list.presentation.ChatListState as State
import ru.gortea.petter.chat.list.presentation.ChatListUiEvent as UiEvent

internal class ChatListReducer(
    private val router: Router<ChatListNavTarget>
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when (event) {
            is Event.LastMessageUpdate -> lastMessageUpdated(event.lastMassage)
            is Event.ChatListLoadingStatus -> state { copy(chats = event.state) }
            is Event.InitApi -> commands(Command.InitLoadChatList)
            is Event.ObserveLastMessage -> commands(Command.ObserveLastMessage)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.lastMessageUpdated(message: MessageModel) {
        state {
            val updatedState = chats.mapContentSync { list ->
                val updatedRoom = list.find { it.id == message.roomId }

                if (updatedRoom == null) {
                    commands(Command.InvalidateList)
                    list
                } else {
                    list.map { room ->
                        if (room.id == message.roomId) room.copy(lastMessage = message) else room
                    }
                }
            }

            copy(chats = updatedState)
        }
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadChats -> commands(Command.InvalidateList)
            is UiEvent.OpenChat -> router.navigateTo(ChatListNavTarget.Chat(event.userId))
        }
    }
}
