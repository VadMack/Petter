package ru.gortea.petter.pet.presentation.state

import ru.gortea.petter.data.model.DataState

internal data class PetState(
    val isCreation: Boolean,
    val editAvailable: Boolean = false,
    val editMode: Boolean = isCreation,
    val petLoadingStatus: DataState<PetPresentationModel> = DataState.Empty,
    val petUpdateStatus: DataState<Unit> = DataState.Empty,
    val petDeleteStatus: DataState<Unit> = DataState.Empty
)
