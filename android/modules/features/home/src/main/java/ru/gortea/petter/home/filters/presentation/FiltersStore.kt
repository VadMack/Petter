package ru.gortea.petter.home.filters.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.pet.list.model.PetListKeyModel

internal typealias FiltersStore = MviStore<FiltersState, FiltersUiEvent>

internal fun createFilterStore(
    router: Router<*>,
    keyModel: PetListKeyModel
): FiltersStore {
    return TeaStore(
        initialState = FiltersState.fromKeyModel(keyModel),
        reducer = FiltersReducer(router)
    )
}
