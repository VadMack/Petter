package ru.gortea.petter.home.filters.presentation.filters

import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.invalid
import ru.gortea.petter.arch.android.util.valid

internal data class RangeFilter(
    override val titleRes: Int,
    val minLimit: FieldState,
    val maxLimit: FieldState
) : Filter(titleRes) {

    fun validated(): RangeFilter {
        return RangeFilter(
            titleRes = titleRes,
            minLimit = minLimit.validated(),
            maxLimit = maxLimit.validated()
        )
    }

    private fun FieldState.validated(): FieldState {
        if (text.isEmpty()) return valid()

        val num = text.toIntOrNull() ?: return invalid()
        if (num < 0) return invalid()

        return valid()
    }
}
