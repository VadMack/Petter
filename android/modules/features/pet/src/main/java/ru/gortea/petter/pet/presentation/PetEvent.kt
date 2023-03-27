package ru.gortea.petter.pet.presentation

import android.net.Uri
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.presentation.state.PetField

internal sealed interface PetEvent {
    class LoadPetStatus(val state: DataState<PetFullModel?>) : PetEvent
    class UpdatePetStatus(val state: DataState<Unit>) : PetEvent
    class DeletePetStatus(val state: DataState<Unit>) : PetEvent

    class IsMyPet(val isMyPet: Boolean) : PetEvent

    object InitApi : PetEvent
}

internal sealed interface PetUiEvent : PetEvent {
    class AvatarChanged(val uri: Uri) : PetUiEvent

    class LoadPet(val id: String?) : PetUiEvent
    class EditField(val field: PetField) : PetUiEvent
    class AddFields(val fields: List<PetField>) : PetUiEvent

    object AvatarClicked : PetUiEvent
    object AvatarEditClicked : PetUiEvent
    object AvatarDeleteClicked : PetUiEvent

    object OpenChat : PetUiEvent
    object GoBack : PetUiEvent
    object DeletePet : PetUiEvent
    object ShowPet : PetUiEvent
    object HidePet : PetUiEvent
    object EditPet : PetUiEvent
    object UpdatePet : PetUiEvent
}
