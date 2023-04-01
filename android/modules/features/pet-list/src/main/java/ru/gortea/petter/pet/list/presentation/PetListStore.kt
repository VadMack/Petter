package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.pet.list.di.PetListComponent
import ru.gortea.petter.pet.list.presentation.actors.PetListInitActor
import ru.gortea.petter.pet.list.presentation.actors.PetListInvalidateActor
import ru.gortea.petter.pet.list.presentation.actors.PetListLoadPageActor
import ru.gortea.petter.pet.list.presentation.actors.PetListUserActor

internal typealias PetListStore = MviStore<PetListState, PetListEvent, Nothing>

internal fun createPetListStore(
    component: PetListComponent,
    pageSize: Int,
    openPetCard: (String) -> Unit
): PetListStore {
    val listRepo = component.petListRepository

    return TeaStore(
        initialState = PetListState(pageSize = pageSize),
        reducer = PetListReducer(openPetCard),
        actors = listOf(
            PetListInitActor(listRepo),
            PetListInvalidateActor(listRepo),
            PetListLoadPageActor(listRepo),
            PetListUserActor(component.userLocalRepository)
        ),
        initialEvents = listOf(PetListEvent.InitApi)
    )
}
