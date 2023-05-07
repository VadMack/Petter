package ru.gortea.petter.profile.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.controller.LogoutController
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.ProfileCommand as Command
import ru.gortea.petter.profile.presentation.ProfileEvent as Event
import ru.gortea.petter.profile.presentation.ProfileState as State
import ru.gortea.petter.profile.presentation.ProfileUiEvent as UiEvent

internal class ProfileReducer(
    private val logoutController: LogoutController,
    private val router: Router<ProfileNavTarget>,
    private val finish: () -> Unit
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when (event) {
            is Event.LoadUserStatus -> loadUserStatus(event.state)
            is Event.IsCurrentUser -> state { copy(isCurrentUser = event.isCurrentUser) }
            is Event.InitApi -> commands(Command.InitLoadUser)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.loadUserStatus(state: DataState<UserModel>) {
        state { copy(userModelStatus = state) }
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadUser -> commands(
                Command.LoadUser(event.id, forceRemote = false),
                Command.IsCurrentUser(event.id)
            )
            is UiEvent.InvalidateUser -> commands(
                Command.LoadUser(event.id, forceRemote = true),
                Command.IsCurrentUser(event.id)
            )
            is UiEvent.EditProfile -> router.navigateTo(ProfileNavTarget.EditProfile)
            is UiEvent.Back -> router.pop()
            is UiEvent.Logout -> logout()
            is UiEvent.AddPet -> router.navigateTo(ProfileNavTarget.AddPet)
            is UiEvent.OpenPet -> router.navigateTo(ProfileNavTarget.OpenPet(event.id))
            is UiEvent.OpenMyPets -> state { copy(petsListState = PetsListState.MINE) }
            is UiEvent.OpenFavourites -> state { copy(petsListState = PetsListState.FAVOURITES) }
        }
    }

    private fun logout() {
        logoutController.logout()
        finish()
    }
}
