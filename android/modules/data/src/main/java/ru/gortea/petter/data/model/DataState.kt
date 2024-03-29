package ru.gortea.petter.data.model

sealed interface DataState<out T> {
    object Empty : DataState<Nothing>

    open class Loading<T> : DataState<T> {
        data class WithError(val reason: Throwable) : Loading<Nothing>()
    }

    data class Fail(val reason: Throwable) : DataState<Nothing>

    data class Content<T>(val content: T, val refreshing: Boolean = false) : DataState<T>
}

val DataState<*>.isContent: Boolean
    get() = this is DataState.Content

val DataState<*>.isLoading: Boolean
    get() = this is DataState.Loading

val DataState<*>.isFail: Boolean
    get() = this is DataState.Fail

suspend fun <T, R> DataState<T>.mapContent(mapper: suspend (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Content -> DataState.Content(mapper(content))
        is DataState.Loading.WithError -> this
        is DataState.Loading -> DataState.Loading()
        is DataState.Fail -> this
        is DataState.Empty -> this
    }
}

fun <T, R> DataState<T>.mapContentSync(mapper: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Content -> DataState.Content(mapper(content))
        is DataState.Loading.WithError -> this
        is DataState.Loading -> DataState.Loading()
        is DataState.Fail -> this
        is DataState.Empty -> this
    }
}
