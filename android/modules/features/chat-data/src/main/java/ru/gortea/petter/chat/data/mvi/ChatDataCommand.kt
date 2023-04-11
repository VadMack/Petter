package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.chat.data.model.ServerMessage

internal sealed interface ChatDataCommand {
    class AddToMessagesList(val message: ServerMessage) : ChatDataCommand

    class SendMessage(val message: String) : ChatDataCommand

    object InitMessagesLoad : ChatDataCommand
    object LoadPage : ChatDataCommand

    object SubscribeToMessages : ChatDataCommand
    object CloseConnection : ChatDataCommand
}
