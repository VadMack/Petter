package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.auth.data.model.RegistrationSuccessModel
import ru.gortea.petter.auth.registration.registration_form.presentation.state.RegistrationState
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.data.model.DataState

internal sealed interface RegistrationEvent {
    class Validated(
        val state: RegistrationState,
        val failedReasons: List<RegistrationFailedReason>
    ) : RegistrationEvent

    data class AccountCreateProcess(
        val status: DataState<RegistrationSuccessModel>
    ) : RegistrationEvent
}

internal interface RegistrationUiEvent : RegistrationEvent {
    class EmailChanged(val email: String) : RegistrationUiEvent
    class UsernameChanged(val username: String) : RegistrationUiEvent
    class PasswordChanged(val password: String) : RegistrationUiEvent
    class PasswordConfirmChanged(val passwordConfirm: String) : RegistrationUiEvent

    object CreateAccount : RegistrationUiEvent
}

