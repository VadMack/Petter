package ru.gortea.petter.auth.registration.registration_form.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.RegistrationModel
import ru.gortea.petter.auth.registration.common.FieldState
import ru.gortea.petter.auth.registration.common.invalid
import ru.gortea.petter.auth.registration.common.text
import ru.gortea.petter.auth.registration.common.valid
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_EMAIL
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.INVALID_USERNAME
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.NONE
import ru.gortea.petter.auth.registration.registration_form.presentation.validation.reason.RegistrationFailedReason.PASSWORDS_ARE_DIFFERENT
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationCommand as Command
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationEvent as Event
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationState as State
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent as UiEvent

internal class RegistrationReducer : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.AccountCreateStatus -> state { copy(registrationStatus = event.state) }
            is Event.Validated -> stateValidated(event)
            is Event.InitApi -> commands(Command.InitCreateAccount)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.stateValidated(event: Event.Validated) {
        if (event.failedReasons.isEmpty()) {
            stateValid(event.state)
        } else {
            state { stateInvalid(event.failedReasons) }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.stateValid(state: State) {
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

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
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
