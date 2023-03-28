package ru.gortea.petter.pet.presentation

import android.net.Uri
import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.navigation.PetNavTarget
import ru.gortea.petter.pet.presentation.state.PetField
import ru.gortea.petter.pet.presentation.state.editMode
import ru.gortea.petter.pet.presentation.state.getDefaultPresentationModel
import ru.gortea.petter.pet.presentation.state.toPetPresentationModel
import ru.gortea.petter.pet.presentation.state.updateField
import ru.gortea.petter.pet.presentation.PetCommand as Command
import ru.gortea.petter.pet.presentation.PetEvent as Event
import ru.gortea.petter.pet.presentation.PetUiEvent as UiEvent
import ru.gortea.petter.pet.presentation.state.PetState as State

internal class PetReducer(
    private val router: PetterRouter<PetNavTarget>,
    private val showModalImageChooser: () -> Unit,
    private val showImagePicker: () -> Unit
) : Reducer<State, Event, Nothing, Command>() {

    override fun MessageBuilder<State, Nothing, Command>.reduce(event: Event) {
        when (event) {
            is Event.InitApi -> commands(
                Command.InitPetLoad,
                Command.InitPetUpdate,
                Command.InitPetCreate,
                Command.InitPetDelete
            )
            is Event.IsMyPet -> state { copy(editAvailable = event.isMyPet) }
            is Event.LoadPetStatus -> loadPetStatus(event)
            is Event.UpdatePetStatus -> updatePetStatus(event)
            is Event.DeletePetStatus -> deletePetStatus(event)
            is UiEvent -> handleUiEvent(event)
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.deletePetStatus(event: Event.DeletePetStatus) {
        state { copy(petDeleteStatus = event.state) }
    }

    private fun MessageBuilder<State, Nothing, Command>.loadPetStatus(event: Event.LoadPetStatus) {
        state {
            val dataState = event.state.mapContentSync {
                it?.toPetPresentationModel(state.editMode) ?: getDefaultPresentationModel()
            }

            copy(petLoadingStatus = dataState)
        }

        if (event.state is DataState.Content) {
            commands(Command.IsMyPet(event.state.content))
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.updatePetStatus(event: Event.UpdatePetStatus) {
        state { copy(petUpdateStatus = event.state) }

        if (event.state is DataState.Content) {
            router.pop()
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadPet -> commands(Command.LoadPet(event.id))
            is UiEvent.UpdatePet -> updatePet()
            is UiEvent.EditField -> editField(event.field)
            is UiEvent.AddFields -> addFields(event.fields)
            is UiEvent.GoBack -> goBack()
            is UiEvent.OpenChat -> Unit // todo open chat with owner
            is UiEvent.EditPet -> editPet()
            is UiEvent.HidePet -> hidePet()
            is UiEvent.ShowPet -> showPet()
            is UiEvent.DeletePet -> deletePet()
            is UiEvent.AvatarChanged -> avatarChanged(event.uri)
            is UiEvent.AvatarClicked -> avatarClicked()
            is UiEvent.AvatarDeleteClicked -> avatarDeleteClicked()
            is UiEvent.AvatarEditClicked -> showImagePicker()
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.addFields(fields: List<PetField>) {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync {
                    val newList = it.fields.toMutableList()
                    newList.addAll(fields)
                    it.copy(fields = newList)
                }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.deletePet() {
        val dataState = state.petLoadingStatus
        if (dataState is DataState.Content) {
            dataState.content.model?.id?.let { commands(Command.DeletePet(it)) }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.avatarClicked() {
        val dataState = state.petLoadingStatus
        if (dataState is DataState.Content) {
            if (dataState.content.photo == null) {
                showImagePicker()
            } else {
                showModalImageChooser()
            }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.avatarDeleteClicked() {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync { it.copy(photo = null) }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.avatarChanged(photo: Uri) {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync { it.copy(photo = photo) }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.updatePet() {
        val status = state.petLoadingStatus
        if (status is DataState.Content) {
            val validated = status.content.fields.map { it.validated() }
            if (validated.any { !it.valid }) {
                state {
                    copy(
                        petLoadingStatus = petLoadingStatus.mapContentSync { it.copy(fields = validated) }
                    )
                }
            } else {
                commands(Command.UpdatePet(status.content))
            }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.editField(field: PetField) {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync { it.updateField(field) }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.goBack() {
        router.pop()

        if (state.editMode && !state.isCreation) {
            state {
                copy(
                    editMode = false,
                    petLoadingStatus = petLoadingStatus.mapContentSync { it.editMode(false) }
                )
            }
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.editPet() {
        val status = state.petLoadingStatus

        if (status is DataState.Content) {
            router.navigateTo(PetNavTarget.EditPet(status.content.model?.id))
        }

        state {
            copy(
                editMode = true,
                petLoadingStatus = petLoadingStatus.mapContentSync { it.editMode(true) }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.hidePet() {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync {
                    it.copy(
                        model = it.model?.copy(state = PetCardState.CLOSED)
                    )
                }
            )
        }
    }

    private fun MessageBuilder<State, Nothing, Command>.showPet() {
        state {
            copy(
                petLoadingStatus = petLoadingStatus.mapContentSync {
                    it.copy(
                        model = it.model?.copy(state = PetCardState.OPEN)
                    )
                }
            )
        }
    }
}
