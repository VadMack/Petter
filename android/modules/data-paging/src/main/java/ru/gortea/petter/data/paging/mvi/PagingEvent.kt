package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.data.paging.model.RequestState

internal sealed interface PagingEvent {
    sealed interface Internal : PagingEvent {
        class LoadingComplete<T>(val data: List<T>) : Internal
        class LoadingFailed(val reason: Throwable) : Internal
    }

    sealed interface User : PagingEvent {
        class LoadPage(val offset: Int = 0) : User

        class Invalidate<T : RequestState>(val args: T, val refresh: Boolean) : User
    }
}
