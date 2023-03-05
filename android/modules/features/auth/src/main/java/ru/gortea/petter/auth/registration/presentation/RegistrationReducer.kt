package ru.gortea.petter.auth.registration.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.registration.presentation.state.RegistrationFieldState
import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.registration.presentation.validation.reason.RegistrationFailedReason.*
import ru.gortea.petter.auth.registration.presentation.RegistrationCommand as Command
import ru.gortea.petter.auth.registration.presentation.RegistrationEvent as Event
import ru.gortea.petter.auth.registration.presentation.state.RegistrationState as State
import ru.gortea.petter.auth.registration.presentation.RegistrationUiEvent as UiEvent

internal class RegistrationReducer : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when(event) {
            is Event.AccountCreateProcess -> state { copy(registrationStatus = event.status) }
            is Event.Validated -> stateValidated(event)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.stateValidated(event: Event.Validated) {
        if (event.failedReasons.isEmpty()) {
            commands(Command.CreateAccount(event.state.toRegistrationModel()))
        } else {
            state { stateInvalid(event.failedReasons) }
        }
    }

    private fun State.stateInvalid(failedReasons: List<RegistrationFailedReason>): State {
        var state = this
        failedReasons.forEach { reason ->
            when(reason) {
                PASSWORDS_ARE_DIFFERENT -> state = state.copy(
                    password = state.password.copy(isValid = false),
                    passwordConfirm = state.passwordConfirm.copy(isValid = false)
                )
                INVALID_EMAIL -> state = state.copy(
                    email = state.email.copy(isValid = false)
                )
                NONE -> Unit
            }
        }
        return state
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when(event) {
            is UiEvent.CreateAccount -> commands(Command.Validate(state))
            is UiEvent.EmailChanged -> state {
                copy(email = RegistrationFieldState(text = event.email.trim()))
            }
            is UiEvent.UsernameChanged -> state {
                copy(username = RegistrationFieldState(text = event.username.trim()))
            }
            is UiEvent.PasswordChanged -> state {
                copy(
                    password = password.copy(
                        text = event.password.trim(),
                        isValid = true
                    ),
                    passwordConfirm = passwordConfirm.copy(isValid = true)
                )
            }
            is UiEvent.PasswordConfirmChanged -> state {
                copy(
                    password = password.copy(isValid = true),
                    passwordConfirm = passwordConfirm.copy(
                        text = event.passwordConfirm.trim(),
                        isValid = true
                    )
                )
            }
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
