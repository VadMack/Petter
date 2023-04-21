package ru.gortea.petter.data.paging.android

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.data.paging.model.isLoading

@Composable
fun rememberPagingState(
    loadNextPage: () -> Unit,
    state: PagingDataState.Paged<*>,
    offset: Int
): LazyListState {
    val lazyColumnListState = rememberLazyListState()

    val needNextPage by remember {
        derivedStateOf {
            val layoutInfo = lazyColumnListState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = layoutInfo.totalItemsCount

            println("xxx: $totalItems $offset $lastVisibleItem")

            lastVisibleItem >= totalItems - offset
        }
    }

    LaunchedEffect(key1 = needNextPage, key2 = state) {
        if (needNextPage && !state.isLoading()) {
            loadNextPage()
        }
    }

    return lazyColumnListState
}
