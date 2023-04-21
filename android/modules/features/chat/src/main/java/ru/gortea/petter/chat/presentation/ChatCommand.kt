package ru.gortea.petter.chat.presentation

internal sealed interface ChatCommand {
    class CreateChatRoomId(val userId: String) : ChatCommand
    class SendMessage(val message: String) : ChatCommand

    object LoadPage : ChatCommand
    object InitMessages : ChatCommand
    object InitChatRoomCreation : ChatCommand
}
