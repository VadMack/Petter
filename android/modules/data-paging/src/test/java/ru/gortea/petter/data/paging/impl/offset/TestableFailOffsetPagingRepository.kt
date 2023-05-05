package ru.gortea.petter.data.paging.impl.offset

import kotlinx.coroutines.delay
import ru.gortea.petter.data.paging.SourceOffsetPagingRepository
import ru.gortea.petter.data.paging.impl.offset.model.TestableOffsetState

class TestableFailOffsetPagingRepository : SourceOffsetPagingRepository<TestableOffsetState, Int>(
    initialState = TestableOffsetState(),
    invalidatePageMapper = { it.copy(offset = 0) },
    offsetUpdater = { offset, state -> state.copy(offset = offset) },
    source = {
        delay(100)
        throw Exception()
    }
)
