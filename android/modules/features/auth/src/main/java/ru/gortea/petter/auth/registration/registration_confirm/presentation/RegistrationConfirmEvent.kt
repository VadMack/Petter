package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface RegistrationConfirmEvent {
    class ConfirmationStatus(val dataState: DataState<Unit>) : RegistrationConfirmEvent
    class ResendCodeStatus(val dataState: DataState<UserModel>) : RegistrationConfirmEvent
    class AuthorizationStatus(val dataState: DataState<UserModel>) : RegistrationConfirmEvent
    class CodeValidated(val isValid: Boolean) : RegistrationConfirmEvent

    object UserUpdated : RegistrationConfirmEvent
    object InitApi : RegistrationConfirmEvent
}

internal sealed interface RegistrationConfirmUiEvent : RegistrationConfirmEvent {
    class CodeChanged(val text: String) : RegistrationConfirmUiEvent

    object Confirm : RegistrationConfirmUiEvent

    object ResendCode : RegistrationConfirmUiEvent

    object Back : RegistrationConfirmUiEvent
}
