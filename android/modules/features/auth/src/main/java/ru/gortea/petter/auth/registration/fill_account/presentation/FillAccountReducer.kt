package ru.gortea.petter.auth.registration.fill_account.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.auth.common.invalid
import ru.gortea.petter.auth.common.text
import ru.gortea.petter.auth.registration.fill_account.presentation.validation.reason.FillAccountFailedReason
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.profile.data.remote.model.AddressModel
import ru.gortea.petter.profile.data.remote.model.AvatarModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateModel
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountAction as Action
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountCommand as Command
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountEvent as Event
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountState as State
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountUiEvent as UiEvent

internal class FillAccountReducer(
    private val finish: () -> Unit
) : Reducer<State, Event, Action, Command>() {

    override fun MessageBuilder<State, Action, Command>.reduce(event: Event) {
        when (event) {
            is Event.UserUpdateStatus -> userUpdateStatus(event.state)
            is Event.Validated -> validated(event.failedReasons)
            is Event.InitApi -> commands(Command.InitUpdateUser)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Action, Command>.userUpdateStatus(state: DataState<Unit>) {
        state { copy(userUpdateStatus = state) }

        if (state.isContent) {
            finish()
        }
    }

    private fun MessageBuilder<State, Action, Command>.validated(
        failedReasons: List<FillAccountFailedReason>
    ) {
        if (failedReasons.isEmpty()) {
            stateValid()
        } else {
            state { stateInvalid(failedReasons) }
        }
    }

    private fun MessageBuilder<State, Action, Command>.stateValid() {
        commands(Command.UpdateUser(state.toUserUpdateFullModel()))
    }

    private fun State.stateInvalid(failedReasons: List<FillAccountFailedReason>): State {
        var state = this
        failedReasons.forEach {
            when (it) {
                FillAccountFailedReason.INVALID_NAME -> {
                    state = state.copy(nameFieldState = nameFieldState.invalid())
                }
                FillAccountFailedReason.INVALID_COUNTRY -> {
                    state = state.copy(countryFieldState = countryFieldState.invalid())
                }
                FillAccountFailedReason.INVALID_CITY -> {
                    state = state.copy(cityFieldState = cityFieldState.invalid())
                }
                FillAccountFailedReason.INVALID_STREET -> {
                    state = state.copy(streetFieldState = streetFieldState.invalid())
                }
                FillAccountFailedReason.NONE -> Unit
            }
        }
        return state
    }

    private fun State.toUserUpdateFullModel(): UserUpdateFullModel {
        val userUpdateModel = UserUpdateModel(
            displayName = nameFieldState.text,
            phoneNumber = "",
            address = AddressModel(
                country = countryFieldState.text,
                city = cityFieldState.text,
                street = streetFieldState.text,
                houseNumber = houseFieldState.text,
                metroStation = ""
            )
        )

        val avatarModel = AvatarModel(filePath = avatar?.toString())

        return UserUpdateFullModel(userUpdateModel, avatarModel)
    }

    private fun MessageBuilder<State, Action, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            UiEvent.AvatarClicked -> avatarClicked()
            UiEvent.AvatarDeleteClicked -> state { copy(avatar = null) }
            UiEvent.AvatarEditClicked -> actions(Action.ShowImagePicker)
            is UiEvent.AvatarChanged -> state { copy(avatar = event.uri) }
            is UiEvent.CityChanged -> state {
                copy(cityFieldState = cityFieldState.text(event.text))
            }
            is UiEvent.CountryChanged -> state {
                copy(countryFieldState = countryFieldState.text(event.text))
            }
            is UiEvent.HouseChanged -> state {
                copy(houseFieldState = houseFieldState.text(event.text))
            }
            is UiEvent.NameChanged -> state {
                copy(nameFieldState = nameFieldState.text(event.text))
            }
            is UiEvent.StreetChanged -> state {
                copy(streetFieldState = streetFieldState.text(event.text))
            }
            UiEvent.UpdateAccount -> commands(Command.Validate(state))
        }
    }

    private fun MessageBuilder<State, Action, Command>.avatarClicked() {
        if (state.avatar == null) {
            actions(Action.ShowImagePicker)
        } else {
            actions(Action.ShowImageChooser)
        }
    }
}
