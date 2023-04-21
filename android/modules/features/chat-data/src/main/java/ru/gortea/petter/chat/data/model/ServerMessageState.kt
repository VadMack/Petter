package ru.gortea.petter.chat.data.model

internal sealed interface ServerMessageState {
    data class Fail(val reason: Throwable) : ServerMessageState
    data class Content(val content: ServerMessage) : ServerMessageState
}
