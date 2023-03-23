package ru.gortea.petter.profile.edit.presentation

import android.net.Uri
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason

internal sealed interface ProfileEditEvent {
    class UserUpdateStatus(val state: DataState<Unit>) : ProfileEditEvent
    class Validated(val failedReasons: List<ProfileEditFailedReason>) : ProfileEditEvent
    class LocalUser(val userModel: UserModel) : ProfileEditEvent

    object GetLocalUser : ProfileEditEvent
    object InitApi : ProfileEditEvent
}

internal sealed interface ProfileEditUiEvent : ProfileEditEvent {
    class AvatarChanged(val uri: Uri) : ProfileEditUiEvent

    class NameChanged(val text: String) : ProfileEditUiEvent
    class CountryChanged(val text: String) : ProfileEditUiEvent
    class CityChanged(val text: String) : ProfileEditUiEvent
    class StreetChanged(val text: String) : ProfileEditUiEvent
    class HouseChanged(val text: String) : ProfileEditUiEvent

    object AvatarClicked : ProfileEditUiEvent
    object AvatarEditClicked : ProfileEditUiEvent
    object AvatarDeleteClicked : ProfileEditUiEvent

    object UpdateAccount : ProfileEditUiEvent
}
