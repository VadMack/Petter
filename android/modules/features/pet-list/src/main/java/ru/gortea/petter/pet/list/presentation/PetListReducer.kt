package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.data.paging.model.isFail
import ru.gortea.petter.data.paging.model.mapContent
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.model.toDataKey
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
            is UiEvent.Refresh -> refresh(event.key)
            is UiEvent.LoadPage -> loadPage()
            is UiEvent.ReloadPage -> reloadPage()
            is UiEvent.LikePet -> likePet(event.id)
            is UiEvent.DislikePet -> dislikePet(event.id)
            is UiEvent.OpenPet -> openPetCard(event.id)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.invalidate(key: PetListKeyModel) {
        val dataKey = key.toDataKey()
        commands(Command.Invalidate(dataKey))
    }

    private fun MessageBuilder<State, Nothing, Command>.refresh(key: PetListKeyModel) {
        val dataKey = key.toDataKey()
        commands(Command.Invalidate(dataKey, refresh = true))
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

    private fun MessageBuilder<State, Nothing, Command>.likePet(id: String) {
        commands(Command.ChangeLikeStatus(id, true))
        state { updateLiked(id, true) }
    }

    private fun MessageBuilder<State, Nothing, Command>.dislikePet(id: String) {
        commands(Command.ChangeLikeStatus(id, false))
        state { updateLiked(id, false) }
    }

    private fun State.updateLiked(id: String, liked: Boolean): State {
        val newState = dataState.mapContent {
            if (it.id == id) {
                it.copy(liked = liked)
            } else {
                it
            }
        }
        return copy(dataState = newState)
    }
}
