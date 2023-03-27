package ru.gortea.petter.profile.edit.presentation

import android.net.Uri
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.invalid
import ru.gortea.petter.arch.android.util.text
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.profile.data.remote.model.AddressModel
import ru.gortea.petter.profile.data.remote.model.AvatarModel
import ru.gortea.petter.profile.data.remote.model.UserModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateFullModel
import ru.gortea.petter.profile.data.remote.model.UserUpdateModel
import ru.gortea.petter.profile.edit.presentation.validation.reason.ProfileEditFailedReason
import ru.gortea.petter.profile.edit.presentation.ProfileEditCommand as Command
import ru.gortea.petter.profile.edit.presentation.ProfileEditEvent as Event
import ru.gortea.petter.profile.edit.presentation.ProfileEditState as State
import ru.gortea.petter.profile.edit.presentation.ProfileEditUiEvent as UiEvent

internal class ProfileEditReducer(
    private val showModalImageChooser: () -> Unit,
    private val showImagePicker: () -> Unit,
    private val finish: () -> Unit
) : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.UserUpdateStatus -> userUpdateStatus(event.state)
            is Event.Validated -> validated(event.failedReasons)
            is Event.InitApi -> commands(Command.InitUpdateUser)
            is Event.LocalUser -> localUser(event.userModel)
            is Event.GetLocalUser -> commands(Command.GetUserLocalData)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.localUser(user: UserModel) {
        state {
            copy(
                avatar = user.avatarPath?.let(Uri::parse),
                nameFieldState = user.displayName?.let(::FieldState) ?: nameFieldState,
                countryFieldState = user.address?.country?.let(::FieldState) ?: countryFieldState,
                cityFieldState = user.address?.city?.let(::FieldState) ?: cityFieldState,
                streetFieldState = user.address?.street?.let(::FieldState) ?: streetFieldState,
                houseFieldState = user.address?.houseNumber?.let(::FieldState) ?: houseFieldState
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.userUpdateStatus(state: DataState<Unit>) {
        state { copy(userUpdateStatus = state) }

        if (state.isContent) {
            finish()
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.validated(
        failedReasons: List<ProfileEditFailedReason>
    ) {
        if (failedReasons.isEmpty()) {
            stateValid()
        } else {
            state { stateInvalid(failedReasons) }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.stateValid() {
        commands(Command.UpdateUser(state.toUserUpdateFullModel()))
    }

    private fun State.stateInvalid(failedReasons: List<ProfileEditFailedReason>): State {
        var state = this
        failedReasons.forEach {
            when (it) {
                ProfileEditFailedReason.INVALID_NAME -> {
                    state = state.copy(nameFieldState = nameFieldState.invalid())
                }
                ProfileEditFailedReason.INVALID_COUNTRY -> {
                    state = state.copy(countryFieldState = countryFieldState.invalid())
                }
                ProfileEditFailedReason.INVALID_CITY -> {
                    state = state.copy(cityFieldState = cityFieldState.invalid())
                }
                ProfileEditFailedReason.INVALID_STREET -> {
                    state = state.copy(streetFieldState = streetFieldState.invalid())
                }
                ProfileEditFailedReason.NONE -> Unit
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

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            UiEvent.AvatarClicked -> avatarClicked()
            UiEvent.AvatarDeleteClicked -> state { copy(avatar = null) }
            UiEvent.AvatarEditClicked -> showImagePicker()
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

    private fun MessageBuilder<State, Nothing, Command>.avatarClicked() {
        if (state.avatar == null) {
            showImagePicker()
        } else {
            showModalImageChooser()
        }
    }
}
