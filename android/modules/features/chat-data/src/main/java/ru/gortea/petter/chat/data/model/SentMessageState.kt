package ru.gortea.petter.chat.data.model

internal sealed class SentMessageState(val message: SentMessage) {
    class Loading(message: SentMessage) : SentMessageState(message)
    class Success(message: SentMessage) : SentMessageState(message)
    class Fail(message: SentMessage, val reason: Throwable) : SentMessageState(message)
}
