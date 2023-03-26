package ru.gortea.petter.pet.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.pet.di.PetComponent
import ru.gortea.petter.pet.presentation.actors.IsMyPetActor
import ru.gortea.petter.pet.presentation.actors.PetCreateInitActor
import ru.gortea.petter.pet.presentation.actors.PetDeleteActor
import ru.gortea.petter.pet.presentation.actors.PetDeleteInitActor
import ru.gortea.petter.pet.presentation.actors.PetGetActor
import ru.gortea.petter.pet.presentation.actors.PetGetInitActor
import ru.gortea.petter.pet.presentation.actors.PetUpdateActor
import ru.gortea.petter.pet.presentation.actors.PetUpdateInitActor
import ru.gortea.petter.pet.presentation.state.PetState

internal typealias PetStore = MviStore<PetState, PetEvent, Nothing>

internal fun createPetStore(
    petId: String?,
    component: PetComponent,
    showModalImageChooser: () -> Unit,
    showImagePicker: () -> Unit
): PetStore {
    val createRepo = component.createPetRepository
    val updateRepo = component.updatePetRepository
    val deleteRepo = component.deletePetRepository
    val getRepo = component.getPetRepository

    return TeaStore(
        PetState(isCreation = petId == null),
        PetReducer(showModalImageChooser, showImagePicker),
        listOf(
            IsMyPetActor(component.userRepository),
            PetCreateInitActor(createRepo),
            PetUpdateInitActor(updateRepo),
            PetUpdateActor(createRepo, updateRepo),
            PetDeleteInitActor(deleteRepo),
            PetDeleteActor(deleteRepo),
            PetGetInitActor(getRepo),
            PetGetActor(getRepo)
        ),
        listOf(
            PetEvent.InitApi,
            PetUiEvent.LoadPet(petId)
        )
    )
}
