package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.model.UserModel

internal sealed interface RegistrationEvent {
    class Validated(
        val state: RegistrationState,
        val failedReasons: List<RegistrationFailedReason>
    ) : RegistrationEvent

    class AccountCreateStatus(val state: DataState<UserModel>) : RegistrationEvent

    object InitApi : RegistrationUiEvent
}

internal interface RegistrationUiEvent : RegistrationEvent {
    class EmailChanged(val email: String) : RegistrationUiEvent
    class UsernameChanged(val username: String) : RegistrationUiEvent
    class PasswordChanged(val password: String) : RegistrationUiEvent
    class PasswordConfirmChanged(val passwordConfirm: String) : RegistrationUiEvent

    object CreateAccount : RegistrationUiEvent
}

