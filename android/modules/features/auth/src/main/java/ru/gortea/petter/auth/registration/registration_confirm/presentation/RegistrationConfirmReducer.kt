package ru.gortea.petter.auth.registration.registration_confirm.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.invalid
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.data.model.RegistrationConfirmModel
import ru.gortea.petter.auth.data.model.RegistrationEmailModel
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmCommand as Command
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmEvent as Event
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmState as State
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent as UiEvent

internal class RegistrationConfirmReducer(
    private val finish: () -> Unit,
    private val router: Router<AuthorizationNavTarget>
) : Reducer<State, Event, Command>() {

    override fun MessageBuilder<State, Command>.reduce(event: Event) {
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

    private fun MessageBuilder<State, Command>.confirmationStatus(status: DataState<Unit>) {
        state {
            val fieldState = codeState.copy(isValid = status !is DataState.Fail)

            copy(codeState = fieldState, confirmationStatus = status)
        }

        if (status is DataState.Content) {
            commands(
                Command.Authorize(
                    username = state.username,
                    pwd = state.password
                )
            )
        }
    }

    private fun MessageBuilder<State, Command>.resendCodeStatus(
        status: DataState<UserModel>
    ) = state {
        copy(resendCodeStatus = status)
    }

    private fun MessageBuilder<State, Command>.authorizationStatus(
        status: DataState<UserModel>
    ) {
        state { copy(authStatus = status) }
        when (status) {
            is DataState.Loading, is DataState.Empty -> Unit
            is DataState.Content -> finish()
            is DataState.Fail -> Unit  /* Todo show error and navigate to auth */
        }
    }

    private fun MessageBuilder<State, Command>.codeValidated(isValid: Boolean) {
        if (isValid) {
            codeValid()
        } else {
            state { copy(codeState = codeState.invalid()) }
        }
    }

    private fun MessageBuilder<State, Command>.codeValid() {
        commands(Command.Confirm(state.toRegistrationConfirmModel()))
    }

    private fun MessageBuilder<State, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Confirm -> commands(Command.Validate(state.codeState.text))
            is UiEvent.ResendCode -> resendCode()
            is UiEvent.CodeChanged -> state {
                copy(codeState = FieldState(text = event.text.trim()))
            }
            is UiEvent.Back -> router.pop()
        }
    }

    private fun MessageBuilder<State, Command>.resendCode() {
        commands(Command.ResendCode(RegistrationEmailModel(state.email)))
    }

    private fun State.toRegistrationConfirmModel(): RegistrationConfirmModel {
        return RegistrationConfirmModel(
            userId = userId,
            code = codeState.text.toInt()
        )
    }
}
