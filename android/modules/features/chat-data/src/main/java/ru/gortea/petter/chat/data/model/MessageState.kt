package ru.gortea.petter.chat.data.model

sealed interface MessageState {
    class Fail(val reason: Throwable) : MessageState
    class Content(val content: ReceivedMessage) : MessageState
}
