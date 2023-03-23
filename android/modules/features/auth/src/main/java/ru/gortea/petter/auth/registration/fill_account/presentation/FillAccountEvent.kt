package ru.gortea.petter.auth.registration.fill_account.presentation

import android.net.Uri
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason
import ru.gortea.petter.data.model.DataState

internal sealed interface FillAccountEvent {
    class UserUpdateStatus(val state: DataState<Unit>) : FillAccountEvent
    class Validated(val failedReasons: List<FillAccountFailedReason>) : FillAccountEvent

    object InitApi : FillAccountEvent
}

internal sealed interface FillAccountUiEvent : FillAccountEvent {
    class AvatarChanged(val uri: Uri) : FillAccountUiEvent

    class NameChanged(val text: String) : FillAccountUiEvent
    class CountryChanged(val text: String) : FillAccountUiEvent
    class CityChanged(val text: String) : FillAccountUiEvent
    class StreetChanged(val text: String) : FillAccountUiEvent
    class HouseChanged(val text: String) : FillAccountUiEvent

    object AvatarClicked : FillAccountUiEvent
    object AvatarEditClicked : FillAccountUiEvent
    object AvatarDeleteClicked : FillAccountUiEvent

    object UpdateAccount : FillAccountUiEvent
}
