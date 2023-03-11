package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel
import ru.gortea.petter.auth.data.model.RegistrationSuccessModel
import ru.gortea.petter.auth.registration.common.RegistrationFieldState
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isInitial
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand as Command
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent as Event
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState as State
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent as UiEvent

internal class RegistrationConfirmReducer : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.ConfirmationStatus -> confirmationStatus(event.dataState)
            is Event.ResendCodeStatus -> resendCodeStatus(event.dataState)
            is Event.CodeValidated ->codeValidated(event.isValid)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.confirmationStatus(
        status: DataState<Unit>
    ) = state {
        val fieldState = codeState.copy(
            isValid = status !is DataState.Fail
        )
        copy(codeState = fieldState, confirmationStatus = status)
    }

    private fun MessageBuilder<State, Nothing, Command>.resendCodeStatus(
        status: DataState<RegistrationSuccessModel>
    ) = state { copy(resendCodeStatus = status) }

    private fun MessageBuilder<State, Nothing, Command>.codeValidated(isValid: Boolean) {
        if (isValid) {
            codeValid()
        } else {
            state { copy(codeState = codeState.copy(isValid = false)) }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.codeValid() {
        if (state.confirmationStatus.isInitial()) {
            commands(Command.Confirm(state.toRegistrationConfirmModel()))
        } else {
            commands(Command.RetryConfirm(state.toRegistrationConfirmModel()))
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Confirm -> commands(Command.Validate(state.codeState.text))
            is UiEvent.ResendCode -> resendCode()
            is UiEvent.CodeChanged -> state {
                copy(codeState = RegistrationFieldState(text = event.text, isValid = true))
            }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.resendCode() {
        if (state.resendCodeStatus.isInitial()) {
            commands(Command.ResendCode(RegistrationEmailModel(state.email)))
        } else {
            commands(Command.RetryResendCode(RegistrationEmailModel(state.email)))
        }
    }

    private fun State.toRegistrationConfirmModel(): RegistrationConfirmModel {
        return RegistrationConfirmModel(
            userId = userId,
            code = codeState.text.toInt()
        )
    }
}
