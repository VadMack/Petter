package ru.gortea.petter.chat.list.presentation

internal sealed interface ChatListCommand {
    object InvalidateList : ChatListCommand
    object ObserveLastMessage : ChatListCommand
    object InitLoadChatList : ChatListCommand
}
