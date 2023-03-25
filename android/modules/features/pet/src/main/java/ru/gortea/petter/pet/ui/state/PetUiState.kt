package ru.gortea.petter.pet.ui.state

import androidx.annotation.StringRes
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.pet.presentation.state.PetPresentationModel

internal data class PetUiState(
    @StringRes val titleRes: Int?,
    val canDelete: Boolean,
    val canEdit: Boolean,
    val modelStatus: DataState<PetPresentationModel>
)
