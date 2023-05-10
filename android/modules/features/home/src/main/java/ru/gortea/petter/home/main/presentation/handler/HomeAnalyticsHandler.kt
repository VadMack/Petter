package ru.gortea.petter.home.main.presentation.handler

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.home.analytics.HomeAnalyticsController
import ru.gortea.petter.home.main.presentation.HomeState
import ru.gortea.petter.home.main.presentation.HomeUiEvent

internal class HomeAnalyticsHandler(
    private val controller: HomeAnalyticsController
) : AnalyticsHandler<HomeState, HomeUiEvent> {

    override fun logEvent(state: HomeState, event: HomeUiEvent) {
        when(event) {
            is HomeUiEvent.HomeOpened -> controller.homeOpened()
            else -> Unit
        }
    }
}
