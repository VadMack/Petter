package ru.gortea.petter.data.paging.model

open class OffsetState(
    open val offset: Int,
    override val pageSize: Int
) : RequestState
