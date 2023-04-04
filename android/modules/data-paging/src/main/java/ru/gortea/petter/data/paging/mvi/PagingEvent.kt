package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.data.paging.model.PageState

internal sealed interface PagingEvent {
    sealed interface Internal : PagingEvent {
        class LoadingComplete<T>(val data: List<T>) : Internal
        class LoadingFailed(val reason: Throwable) : Internal
    }

    sealed interface User : PagingEvent {
        object LoadPage: User

        class Invalidate<T: PageState>(val args: T, val refresh: Boolean) : User
    }
}
