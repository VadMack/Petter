package ru.gortea.petter.data.model

sealed interface DataState<out T> {
    object Empty : DataState<Nothing>

    open class Loading<T> : DataState<T> {
        data class WithContent<T>(val content: T) : Loading<T>()
        data class WithError(val reason: Throwable) : Loading<Nothing>()
    }

    data class Fail(val reason: Throwable) : DataState<Nothing>

    data class Content<T>(val content: T) : DataState<T>
}

fun DataState<*>.isInitial(): Boolean = this is DataState.Empty
