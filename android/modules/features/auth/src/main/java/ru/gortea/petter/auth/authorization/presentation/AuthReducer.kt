package ru.gortea.petter.auth.authorization.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.NavTarget
import ru.gortea.petter.navigation.graph.RegistrationFlowNavTarget
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.auth.authorization.presentation.AuthCommand as Command
import ru.gortea.petter.auth.authorization.presentation.AuthEvent as Event
import ru.gortea.petter.auth.authorization.presentation.AuthState as State
import ru.gortea.petter.auth.authorization.presentation.AuthUiEvent as UiEvent

internal class AuthReducer(
    private val router: PetterRouter<NavTarget>
) : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.InitApi -> commands(Command.InitAuthorize)
            is Event.AuthorizationStatus -> authorizationStatus(event.state)
            is Event.UserUpdated -> userUpdated()
            is Event.Validated -> validated(event)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.authorizationStatus(state: DataState<UserModel>) {
        state {
            copy(
                userUpdated = false,
                authStatus = state
            )
        }

        if (state is DataState.Content) {
            commands(Command.UpdateUser(state.content))
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.userUpdated() {
        state { copy(userUpdated = true) }
        // TODO navigate to main
    }

    private fun MessageBuilder<State, Nothing, Command>.validated(event: Event.Validated) = state {
        if (event.pwdValid && event.usernameValid) {
            val model = CredsAuthorizationModel(
                username = username.text,
                password = password.text
            )

            commands(Command.Authorize(model))
        }

        copy(
            username = username.copy(isValid = event.usernameValid),
            password = password.copy(isValid = event.pwdValid)
        )
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Authorize -> authorize()
            is UiEvent.Registration -> router.navigateTo(RegistrationFlowNavTarget.RegistrationForm)
            is UiEvent.UsernameChanged -> usernameChanged(event.text)
            is UiEvent.PasswordChanged -> passwordChanged(event.text)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.authorize() {
        val validate = Command.Validate(
            username = state.username.text,
            pwd = state.password.text
        )
        commands(validate)
    }

    private fun MessageBuilder<State, Nothing, Command>.usernameChanged(text: String) {
        state {
            copy(username = username.copy(text = text, isValid = true))
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.passwordChanged(text: String) {
        state {
            copy(password = password.copy(text = text, isValid = true))
        }
    }
}
