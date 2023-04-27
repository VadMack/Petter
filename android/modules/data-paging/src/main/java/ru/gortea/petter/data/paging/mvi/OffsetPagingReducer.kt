package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.paging.model.OffsetState
import ru.gortea.petter.data.paging.model.PagingDataState.Initial
import ru.gortea.petter.data.paging.model.PagingDataState.Paged
import ru.gortea.petter.data.paging.mvi.PagingEvent.Internal
import ru.gortea.petter.data.paging.mvi.PagingEvent.User

@Suppress("UNCHECKED_CAST")
internal class OffsetPagingReducer<T : OffsetState>(
    private val invalidatePageMapper: (T) -> T,
    private val offsetUpdater: (Int, T) -> T
) : Reducer<PagingState<T>, PagingEvent, Nothing, PagingCommand>() {

    override fun MessageBuilder<PagingState<T>, Nothing, PagingCommand>.reduce(event: PagingEvent) {
        when (event) {
            is Internal -> handleInternalEvent(event)
            is User -> handleUserEvent(event)
        }
    }

    private fun MessageBuilder<PagingState<T>, Nothing, PagingCommand>.handleInternalEvent(event: Internal) {
        when (event) {
            is Internal.LoadingComplete<*> -> state {
                val prevList =
                    if (dataState is Paged.Refresh) emptyList() else dataState.content
                val lastPage = event.data.size < pageState.pageSize
                copy(
                    lastPage = lastPage,
                    dataState = Paged.Content(prevList + event.data)
                )
            }
            is Internal.LoadingFailed -> state {
                val newState = when (dataState) {
                    is Initial -> Initial.Fail(event.reason)
                    is Paged -> Paged.Fail(
                        dataState.content,
                        event.reason
                    )
                }

                copy(dataState = newState)
            }
        }
    }

    private fun MessageBuilder<PagingState<T>, Nothing, PagingCommand>.handleUserEvent(event: User) {
        when (event) {
            is User.Invalidate<*> -> state {
                val invalidatePage = invalidatePageMapper(event.args as T)
                commands(PagingCommand.LoadPageByOffset(invalidatePage))

                copy(
                    pageState = invalidatePage,
                    dataState = if (event.refresh) Paged.Refresh(dataState.content) else Initial.Loading
                )
            }
            is User.LoadPage -> state {
                if (lastPage) return@state this
                val pageState = offsetUpdater(event.offset, pageState)
                commands(PagingCommand.LoadPageByOffset(pageState))

                val state =
                    if (dataState is Paged) Paged.Loading(dataState.content) else Initial.Loading

                copy(
                    pageState = pageState,
                    dataState = state
                )
            }
        }
    }
}

