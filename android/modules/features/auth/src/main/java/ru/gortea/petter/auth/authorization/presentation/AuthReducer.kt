package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget.Registration
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.auth.authorization.presentation.AuthCommand as Command
import ru.gortea.petter.auth.authorization.presentation.AuthEvent as Event
import ru.gortea.petter.auth.authorization.presentation.AuthState as State
import ru.gortea.petter.auth.authorization.presentation.AuthUiEvent as UiEvent

internal class AuthReducer(
    private val router: Router<AuthorizationNavTarget>,
    private val finish: () -> Unit
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when (event) {
            is Event.InitApi -> commands(Command.InitAuthorize)
            is Event.AuthorizationStatus -> authorizationStatus(event.state)
            is Event.Validated -> validated(event)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.authorizationStatus(state: DataState<UserModel>) {
        state { copy(authStatus = state) }

        if (state.isContent) {
            finish()
        }
    }

    private fun MessageBuilder<State, Command>.validated(event: Event.Validated) = state {
        if (event.pwdValid && event.usernameValid) {
            commands(Command.Authorize(username.text, password.text))
        }

        copy(
            username = username.copy(isValid = event.usernameValid),
            password = password.copy(isValid = event.pwdValid)
        )
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Authorize -> authorize()
            is UiEvent.Registration -> router.navigateTo(Registration.RegistrationForm)
            is UiEvent.UsernameChanged -> usernameChanged(event.text)
            is UiEvent.PasswordChanged -> passwordChanged(event.text)
        }
    }

    private fun MessageBuilder<State, Command>.authorize() {
        val validate = Command.Validate(
            username = state.username.text,
            pwd = state.password.text
        )
        commands(validate)
    }

    private fun MessageBuilder<State, Command>.usernameChanged(text: String) {
        state {
            copy(username = username.copy(text = text, isValid = true))
        }
    }

    private fun MessageBuilder<State, Command>.passwordChanged(text: String) {
        state {
            copy(password = password.copy(text = text, isValid = true))
        }
    }
}
