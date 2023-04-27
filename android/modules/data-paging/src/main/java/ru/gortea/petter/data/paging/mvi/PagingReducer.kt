package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.paging.model.PageState
import ru.gortea.petter.data.paging.model.PagingDataState.Initial
import ru.gortea.petter.data.paging.model.PagingDataState.Paged
import ru.gortea.petter.data.paging.mvi.PagingEvent.Internal
import ru.gortea.petter.data.paging.mvi.PagingEvent.User
import ru.gortea.petter.data.paging.mvi.PagingCommand as Command
import ru.gortea.petter.data.paging.mvi.PagingEvent as Event
import ru.gortea.petter.data.paging.mvi.PagingState as State

@Suppress("UNCHECKED_CAST")
internal class PagingReducer<T : PageState>(
    private val invalidatePageMapper: (T) -> T,
    private val nextPageMapper: (T) -> T
) : Reducer<State<T>, Event, Nothing, Command>() {

    override fun MessageBuilder<State<T>, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Internal -> handleInternalEvent(event)
            is User -> handleUserEvent(event)
        }
    }

    private fun MessageBuilder<State<T>, Nothing, Command>.handleInternalEvent(event: Internal) {
        when (event) {
            is Internal.LoadingComplete<*> -> state {
                val prevList = if (dataState is Paged.Refresh) emptyList() else dataState.content
                val lastPage = event.data.size < pageState.pageSize
                copy(
                    pageState = if (lastPage) pageState else nextPageMapper(pageState),
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

    private fun MessageBuilder<State<T>, Nothing, Command>.handleUserEvent(event: User) {
        when (event) {
            is User.Invalidate<*> -> state {
                val invalidatePage = invalidatePageMapper(event.args as T)
                commands(Command.LoadPage(invalidatePage))

                copy(
                    pageState = invalidatePage,
                    dataState = if (event.refresh) Paged.Refresh(dataState.content) else Initial.Loading
                )
            }
            is User.LoadPage -> state {
                if (lastPage) return@state this
                commands(Command.LoadPage(pageState))

                val state = if (dataState is Paged) Paged.Loading(dataState.content)
                else Initial.Loading

                copy(dataState = state)
            }
        }
    }
}
