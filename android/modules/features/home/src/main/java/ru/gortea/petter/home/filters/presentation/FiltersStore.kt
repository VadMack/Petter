package ru.gortea.petter.home.filters.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.list.model.PetListKeyModel

internal typealias FiltersStore = MviStore<FiltersState, FiltersUiEvent, Nothing>

internal fun createFilterStore(
    router: PetterRouter<*>,
    keyModel: PetListKeyModel
): FiltersStore {
    return TeaStore(
        initialState = FiltersState.fromKeyModel(keyModel),
        reducer = FiltersReducer(router)
    )
}
