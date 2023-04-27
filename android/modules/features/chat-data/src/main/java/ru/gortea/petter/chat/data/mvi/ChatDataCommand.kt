package ru.gortea.petter.chat.data.mvi

internal sealed interface ChatDataCommand {
    class SendMessage(val message: String) : ChatDataCommand
    class LoadPage(val offset: Int) : ChatDataCommand

    object InitMessagesLoad : ChatDataCommand

    object SubscribeToLifecycle : ChatDataCommand
    object SubscribeToMessages : ChatDataCommand
    object CloseConnection : ChatDataCommand
}
