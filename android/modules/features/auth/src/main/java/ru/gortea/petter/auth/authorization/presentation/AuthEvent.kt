package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface AuthEvent {
    class AuthorizationStatus(val state: DataState<UserModel>) : AuthEvent
    class Validated(
        val usernameValid: Boolean,
        val pwdValid: Boolean
    ) : AuthEvent

    object UserUpdated : AuthEvent
    object InitApi : AuthEvent
}

internal sealed interface AuthUiEvent : AuthEvent {
    class UsernameChanged(val text: String): AuthUiEvent
    class PasswordChanged(val text: String): AuthUiEvent

    object Authorize : AuthUiEvent
    object Registration : AuthUiEvent
}
