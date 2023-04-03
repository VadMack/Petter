package ru.gortea.petter.profile.presentation

import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface ProfileEvent {
    class LoadUserStatus(val state: DataState<UserModel>) : ProfileEvent
    class IsCurrentUser(val isCurrentUser: Boolean) : ProfileEvent

    object InitApi : ProfileEvent
}

internal sealed interface ProfileUiEvent : ProfileEvent {
    class LoadUser(val id: String) : ProfileUiEvent
    class OpenPet(val id: String) : ProfileUiEvent

    object OpenMyPets : ProfileUiEvent
    object OpenFavourites : ProfileUiEvent

    object AddPet : ProfileUiEvent
    object EditProfile : ProfileUiEvent
    object Logout : ProfileUiEvent
    object Back : ProfileUiEvent
}
