package ru.gortea.petter.data.model

sealed interface DataState<out T> {
    object Empty : DataState<Nothing>

    open class Loading<T> : DataState<T> {
        class WithContent<T>(val content: T) : Loading<T>()
        class WithError(val reason: Throwable) : Loading<Nothing>()
    }

    class Fail(val reason: Throwable) : DataState<Nothing>

    class Content<T>(val content: T) : DataState<T>
}
