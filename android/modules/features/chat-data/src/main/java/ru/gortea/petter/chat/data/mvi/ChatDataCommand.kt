package ru.gortea.petter.chat.data.mvi

internal sealed interface ChatDataCommand {
    class SendMessage(val message: String) : ChatDataCommand

    object InitMessagesLoad : ChatDataCommand
    object LoadPage : ChatDataCommand

    object SubscribeToLifecycle : ChatDataCommand
    object SubscribeToMessages : ChatDataCommand
    object CloseConnection : ChatDataCommand
}
