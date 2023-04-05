package ru.gortea.petter.home.filters.presentation.filters

import ru.gortea.petter.home.filters.presentation.constants.TitleHolder

internal data class EnumFilter<out T : TitleHolder>(
    override val titleRes: Int,
    private val items: List<T>,
    val selected: T = items.first()
) : Filter(titleRes) {
    val availableItems: List<T>
        get() = items.filter { it != selected }
}
