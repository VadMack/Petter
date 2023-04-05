package ru.gortea.petter.home.filters.presentation.filters

import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.home.R

internal data class SimpleFieldFilter(
    override val titleRes: Int,
    val field: FieldState,
    val placeholderRes: Int = R.string.filter_unknown
) : Filter(titleRes)
