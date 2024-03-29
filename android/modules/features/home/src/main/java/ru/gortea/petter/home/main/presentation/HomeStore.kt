package ru.gortea.petter.home.main.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.home.di.HomeComponent
import ru.gortea.petter.home.main.presentation.handler.HomeAnalyticsHandler
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.navigation.Router

internal typealias HomeStore = MviStore<HomeState, HomeUiEvent>

internal fun createHomeStore(
    router: Router<HomeNavTarget>,
    component: HomeComponent
): HomeStore {
    return TeaStore(
        initialState = HomeState(),
        reducer = HomeReducer(router),
        initialEvents = listOf(HomeUiEvent.HomeOpened),
        analyticsHandler = HomeAnalyticsHandler(component.homeAnalyticsController)
    )
}
