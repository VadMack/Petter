package ru.gortea.petter.home.filters.presentation

import ru.gortea.petter.home.filters.presentation.filters.Filter

internal sealed interface FiltersUiEvent {
    class UpdateFilter(val filter: Filter) : FiltersUiEvent

    object ScreenOpened : FiltersUiEvent
    object Accept : FiltersUiEvent
    object GoBack : FiltersUiEvent
}
