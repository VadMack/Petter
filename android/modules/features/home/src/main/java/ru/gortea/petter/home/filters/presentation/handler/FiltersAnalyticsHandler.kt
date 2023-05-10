package ru.gortea.petter.home.filters.presentation.handler

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.home.analytics.HomeAnalyticsController
import ru.gortea.petter.home.filters.presentation.FiltersState
import ru.gortea.petter.home.filters.presentation.FiltersUiEvent

internal class FiltersAnalyticsHandler(
    private val controller: HomeAnalyticsController
) : AnalyticsHandler<FiltersState, FiltersUiEvent> {

    override fun logEvent(state: FiltersState, event: FiltersUiEvent) {
        when(event) {
            is FiltersUiEvent.ScreenOpened -> controller.filtersOpened()
            else -> Unit
        }
    }
}
