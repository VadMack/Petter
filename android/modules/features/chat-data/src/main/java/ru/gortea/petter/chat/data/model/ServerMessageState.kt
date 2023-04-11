package ru.gortea.petter.chat.data.model

internal sealed interface ServerMessageState {
    class Fail(val reason: Throwable) : ServerMessageState
    class Content(val content: ServerMessage) : ServerMessageState
}
