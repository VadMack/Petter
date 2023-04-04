package ru.gortea.petter.pet.ui.state.showing

import ru.gortea.petter.data.model.DataState

internal data class PetUiState(
    val canDelete: Boolean,
    val canEdit: Boolean,
    val likeStatus: PetLikeStatus,
    val modelStatus: DataState<PetFullUiModel>,
    val isDeleteLoading: Boolean
)
