package ru.gortea.petter.data.paging.impl.offset.model

import ru.gortea.petter.data.paging.model.OffsetState

data class TestableOffsetState(
    override val offset: Int = 0,
    override val pageSize: Int = 1
) : OffsetState(offset, pageSize)
