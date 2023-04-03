package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.pet.list.data.model.PetListItemModel
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface PetListEvent {
    class PetListLoadingStatus(val state: PagingDataState<PetListItemModel>) : PetListEvent
    class CurrentUser(val user: UserModel) : PetListEvent

    object InitApi : PetListEvent
}

internal sealed interface PetListUiEvent : PetListEvent {
    class Invalidate(val key: PetListKeyModel) : PetListUiEvent
    class OpenPet(val id: String) : PetListUiEvent
    class LikePet(val id: String) : PetListUiEvent
    class DislikePet(val id: String) : PetListUiEvent

    object ReloadPage : PetListUiEvent
    object LoadPage : PetListUiEvent
}
