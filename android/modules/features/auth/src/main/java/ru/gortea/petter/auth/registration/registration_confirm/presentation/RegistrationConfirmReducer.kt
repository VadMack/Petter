package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.common.FieldState
import ru.gortea.petter.auth.common.invalid
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget.Registration
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand as Command
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent as Event
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState as State
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent as UiEvent

internal class RegistrationConfirmReducer(
    private val router: Router<AuthorizationNavTarget>
) : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.ConfirmationStatus -> confirmationStatus(event.dataState)
            is Event.ResendCodeStatus -> resendCodeStatus(event.dataState)
            is Event.AuthorizationStatus -> authorizationStatus(event.dataState)
            is Event.CodeValidated -> codeValidated(event.isValid)
            is Event.InitApi -> commands(
                Command.InitResendCode,
                Command.InitConfirm,
                Command.InitAuthorize
            )
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.confirmationStatus(status: DataState<Unit>) {
        state {
            val fieldState = codeState.copy(isValid = status !is DataState.Fail)

            copy(codeState = fieldState, confirmationStatus = status)
        }

        if (status is DataState.Content) {
            commands(Command.Authorize(state.toAuthorizationModel()))
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.resendCodeStatus(
        status: DataState<UserModel>
    ) = state {
        copy(resendCodeStatus = status)
    }

    private fun MessageBuilder<State, Nothing, Command>.authorizationStatus(
        status: DataState<UserModel>
    ) {
        state { copy(authStatus = status) }
        when (status) {
            is DataState.Loading, is DataState.Empty -> Unit
            is DataState.Content -> router.navigateTo(Registration.FillAccount)
            is DataState.Fail -> Unit  /* Todo show error and navigate to auth */
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.codeValidated(isValid: Boolean) {
        if (isValid) {
            codeValid()
        } else {
            state { copy(codeState = codeState.invalid()) }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.codeValid() {
        commands(Command.Confirm(state.toRegistrationConfirmModel()))
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Confirm -> commands(Command.Validate(state.codeState.text))
            is UiEvent.ResendCode -> resendCode()
            is UiEvent.CodeChanged -> state {
                copy(codeState = FieldState(text = event.text.trim(), isValid = true))
            }
            is UiEvent.Back -> router.pop()
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.resendCode() {
        commands(Command.ResendCode(RegistrationEmailModel(state.email)))
    }

    private fun State.toAuthorizationModel(): CredsAuthorizationModel {
        return CredsAuthorizationModel(
            username = username,
            password = password
        )
    }

    private fun State.toRegistrationConfirmModel(): RegistrationConfirmModel {
        return RegistrationConfirmModel(
            userId = userId,
            code = codeState.text.toInt()
        )
    }
}
