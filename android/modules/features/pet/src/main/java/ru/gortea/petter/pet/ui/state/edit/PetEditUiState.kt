package ru.gortea.petter.pet.ui.state.edit

import androidx.annotation.StringRes
import ru.gortea.petter.data.model.DataState

internal data class PetEditUiState(
    @StringRes val titleRes: Int?,
    val modelStatus: DataState<PetEditFullUiModel>
)
