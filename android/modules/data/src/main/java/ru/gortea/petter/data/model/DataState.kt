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

suspend fun<T, R> DataState<T>.mapContent(mapper: suspend (T) -> R): DataState<R> {
    return when(this) {
        is DataState.Loading.WithContent -> DataState.Loading.WithContent(mapper(content))
        is DataState.Content -> DataState.Content(mapper(content))
        is DataState.Loading.WithError -> this
        is DataState.Loading -> DataState.Loading()
        is DataState.Fail -> this
        is DataState.Empty -> this
    }
}
