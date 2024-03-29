package ru.gortea.petter.data.paging.model

sealed class PagingDataState<out T>(open val content: List<T>) {

    sealed class Initial : PagingDataState<Nothing>(emptyList()) {

        object Empty : Initial()

        object Loading : Initial()

        data class Fail(val reason: Throwable) : Initial()
    }

    sealed class Paged<T>(override val content: List<T>) : PagingDataState<T>(content) {
        data class Refresh<T>(override val content: List<T>) : Paged<T>(content)

        data class Loading<T>(override val content: List<T>) : Paged<T>(content)

        data class Content<T>(override val content: List<T>) : Paged<T>(content)

        data class Fail<T>(
            override val content: List<T>,
            val reason: Throwable
        ) : Paged<T>(content)
    }
}

inline fun<T, R> PagingDataState<T>.mapContentList(mapper: (List<T>) -> List<R>): PagingDataState<R> {
    return when(this) {
        is PagingDataState.Initial -> this
        is PagingDataState.Paged.Content -> PagingDataState.Paged.Content(mapper(content))
        is PagingDataState.Paged.Loading -> PagingDataState.Paged.Loading(mapper(content))
        is PagingDataState.Paged.Refresh -> PagingDataState.Paged.Refresh(mapper(content))
        is PagingDataState.Paged.Fail -> PagingDataState.Paged.Fail(mapper(content), reason)
    }
}

inline fun<T, R> PagingDataState<T>.mapContent(mapper: (T) -> R): PagingDataState<R> {
    return when(this) {
        is PagingDataState.Initial -> this
        is PagingDataState.Paged.Content -> PagingDataState.Paged.Content(content.map(mapper))
        is PagingDataState.Paged.Loading -> PagingDataState.Paged.Loading(content.map(mapper))
        is PagingDataState.Paged.Refresh -> PagingDataState.Paged.Refresh(content.map(mapper))
        is PagingDataState.Paged.Fail -> PagingDataState.Paged.Fail(content.map(mapper), reason)
    }
}

fun PagingDataState<*>.isContent(): Boolean {
    return when(this) {
        is PagingDataState.Paged.Content -> true
        else -> false
    }
}

fun PagingDataState<*>.isLoading(): Boolean {
    return when(this) {
        is PagingDataState.Initial.Loading -> true
        is PagingDataState.Paged.Loading -> true
        is PagingDataState.Paged.Refresh -> true
        else -> false
    }
}

fun PagingDataState<*>.isFail(): Boolean {
    return this is PagingDataState.Paged.Fail || this is PagingDataState.Initial.Fail
}
