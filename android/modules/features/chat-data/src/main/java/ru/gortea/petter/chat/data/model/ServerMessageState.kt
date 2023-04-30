package ru.gortea.petter.chat.data.model

import ru.gortea.petter.chat.data.messages.model.ServerMessage

internal sealed interface ServerMessageState {
    data class Fail(val reason: Throwable) : ServerMessageState
    data class Content(val content: ServerMessage) : ServerMessageState
}
