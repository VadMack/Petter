package ru.gortea.petter.home.main.presentation

import ru.gortea.petter.pet.list.model.PetListKeyModel

internal sealed interface HomeUiEvent {
    class OpenPet(val id: String) : HomeUiEvent
    class AcceptFilters(val keyModel: PetListKeyModel) : HomeUiEvent

    object HomeOpened : HomeUiEvent
    object OpenFilters : HomeUiEvent
}
