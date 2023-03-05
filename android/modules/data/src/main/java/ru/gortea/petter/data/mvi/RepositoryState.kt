package ru.gortea.petter.data.mvi

import ru.gortea.petter.data.model.DataState

internal data class RepositoryState(
    val dataState: DataState<*> = DataState.Empty
)
