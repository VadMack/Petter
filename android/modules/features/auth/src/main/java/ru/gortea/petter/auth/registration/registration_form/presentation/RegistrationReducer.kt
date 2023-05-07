package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.invalid
import ru.gortea.petter.arch.android.util.text
import ru.gortea.petter.arch.android.util.valid
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget.Registration
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_EMAIL
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_USERNAME
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.NONE
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.PASSWORDS_ARE_DIFFERENT
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationCommand as Command
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationEvent as Event
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState as State
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent as UiEvent

internal class RegistrationReducer(
    private val router: Router<AuthorizationNavTarget>
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
        when (event) {
            is Event.AccountCreateStatus -> accountCreateStatus(event)
            is Event.Validated -> stateValidated(event)
            is Event.InitApi -> commands(Command.InitCreateAccount)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Command>.accountCreateStatus(event: Event.AccountCreateStatus) {
        state {
            when (event.state) {
                is DataState.Loading, is DataState.Empty -> Unit
                is DataState.Content -> {
                    if (!event.state.refreshing) {
                        navigateToRegistrationConfirm(
                            event.state.content,
                            password.text
                        )
                    }
                }
                is DataState.Fail -> declineRegistration()
            }
            copy(registrationStatus = event.state)
        }
    }

    private fun MessageBuilder<State, Command>.declineRegistration() {
        commands(Command.DeclineRegistration(state.email.text))
    }

    private fun navigateToRegistrationConfirm(user: UserModel, pwd: String) {
        router.navigateTo(
            Registration.RegistrationConfirm(
                email = user.email,
                username = user.username,
                pwd = pwd,
                userId = user.id
            )
        )
    }

    private fun MessageBuilder<State, Command>.stateValidated(event: Event.Validated) {
        if (event.failedReasons.isEmpty()) {
            stateValid(event.state)
        } else {
            state { stateInvalid(event.failedReasons) }
        }
    }

    private fun MessageBuilder<State, Command>.stateValid(state: State) {
        commands(Command.CreateAccount(state.toRegistrationModel()))
    }

    private fun State.stateInvalid(failedReasons: List<RegistrationFailedReason>): State {
        var state = this
        failedReasons.forEach { reason ->
            when (reason) {
                PASSWORDS_ARE_DIFFERENT -> state = state.copy(
                    password = password.invalid(),
                    passwordConfirm = passwordConfirm.invalid()
                )
                INVALID_EMAIL -> state = state.copy(email = email.invalid())
                INVALID_USERNAME -> state = state.copy(username = username.invalid())
                NONE -> Unit
            }
        }
        return state
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.CreateAccount -> commands(Command.Validate(state))
            is UiEvent.EmailChanged -> state {
                copy(email = FieldState(text = event.email.trim()))
            }
            is UiEvent.UsernameChanged -> state {
                copy(username = FieldState(text = event.username.trim()))
            }
            is UiEvent.PasswordChanged -> state {
                copy(
                    password = password.text(event.password.trim()),
                    passwordConfirm = passwordConfirm.valid()
                )
            }
            is UiEvent.PasswordConfirmChanged -> state {
                copy(
                    password = password.valid(),
                    passwordConfirm = passwordConfirm.text(event.passwordConfirm.trim())
                )
            }
            is UiEvent.Back -> router.pop()
            is UiEvent.Authorize -> router.updateRoot(AuthorizationNavTarget.Authorization)
        }
    }

    private fun State.toRegistrationModel(): RegistrationModel {
        return RegistrationModel(
            username = username.text,
            password = password.text,
            email = email.text
        )
    }
}
