package ru.gortea.petter.data.paging.impl.page

import kotlinx.coroutines.delay
import ru.gortea.petter.data.paging.SourcePagingRepository
import ru.gortea.petter.data.paging.impl.page.model.TestablePageState

class TestableFailSecondPageRepository : SourcePagingRepository<TestablePageState, Int>(
    initialState = TestablePageState(),
    invalidatePageMapper = { it.copy(page = 0) },
    nextPageMapper = { it.copy(page = it.page + 1) },
    source = {
        delay(100)
        if (it.page == 1) {
            throw Exception()
        } else {
            listOf(it.page)
        }
    }
)
