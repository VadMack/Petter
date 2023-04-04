package ru.gortea.petter.home.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.navigation.PetterRouter

internal typealias HomeStore = MviStore<HomeState, HomeUiEvent, Nothing>

internal fun createHomeStore(
    router: PetterRouter<HomeNavTarget>
): HomeStore {
    return TeaStore(
        initialState = HomeState(),
        reducer = HomeReducer(router)
    )
}
