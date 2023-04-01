package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.data.paging.model.isFail
import ru.gortea.petter.pet.list.data.model.PetListKey
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.presentation.PetListCommand as Command
import ru.gortea.petter.pet.list.presentation.PetListEvent as Event
import ru.gortea.petter.pet.list.presentation.PetListState as State
import ru.gortea.petter.pet.list.presentation.PetListUiEvent as UiEvent

internal class PetListReducer(
    private val openPetCard: (String) -> Unit
) : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.PetListLoadingStatus -> state { copy(dataState = event.state) }
            is Event.InitApi -> commands(Command.InitPetList, Command.GetCurrentUser)
            is Event.CurrentUser -> state { copy(currentUserId = event.user.id) }
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Invalidate -> invalidate(event.key)
            is UiEvent.LoadPage -> loadPage()
            is UiEvent.ReloadPage -> reloadPage()
            is UiEvent.OpenPet -> openPetCard(event.id)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.invalidate(key: PetListKeyModel) {
        val dataKey = PetListKey(
            favourites = key.favourites,
            ownerId = key.ownerId,
            species = key.species,
            breed = key.breed,
            gender = key.gender,
            pageSize = key.pageSize
        )
        commands(Command.Invalidate(dataKey))
    }

    private fun MessageBuilder<State, Nothing, Command>.loadPage() {
        if (state.dataState is PagingDataState.Paged.Content) {
            commands(Command.LoadPage)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.reloadPage() {
        if (state.dataState.isFail()) {
            commands(Command.LoadPage)
        }
    }
}
