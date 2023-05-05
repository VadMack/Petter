package ru.gortea.petter.data.paging.impl.page.model

import ru.gortea.petter.data.paging.model.PageState

data class TestablePageState(
    override val page: Int = 0,
    override val pageSize: Int = 1
) : PageState(page, pageSize)
