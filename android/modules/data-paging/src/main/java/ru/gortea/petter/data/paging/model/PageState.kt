package ru.gortea.petter.data.paging.model

open class PageState(
    open val page: Int,
    override val pageSize: Int
) : RequestState
