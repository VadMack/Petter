package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.auth.data.model.RegistrationSuccessModel
import ru.gortea.petter.data.model.DataState

internal sealed interface RegistrationConfirmEvent {
    class ConfirmationStatus(val dataState: DataState<Unit>) : RegistrationConfirmEvent

    class ResendCodeStatus(val dataState: DataState<RegistrationSuccessModel>) : RegistrationConfirmEvent
    class CodeValidated(val isValid: Boolean) : RegistrationConfirmEvent
}

internal sealed interface RegistrationConfirmUiEvent : RegistrationConfirmEvent {
    class CodeChanged(val text: String) : RegistrationConfirmUiEvent

    object Confirm : RegistrationConfirmUiEvent

    object ResendCode : RegistrationConfirmUiEvent
}
