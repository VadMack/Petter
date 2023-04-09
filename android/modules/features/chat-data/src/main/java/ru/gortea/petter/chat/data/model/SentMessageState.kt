package ru.gortea.petter.chat.data.model

sealed class SentMessageState(val message: String) {
    class Success(message: String) : SentMessageState(message)
    class Fail(message: String, val reason: Throwable) : SentMessageState(message)
}
