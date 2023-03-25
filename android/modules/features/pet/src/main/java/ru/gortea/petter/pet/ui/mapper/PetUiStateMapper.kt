package ru.gortea.petter.pet.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.pet.R
import ru.gortea.petter.pet.presentation.state.PetState
import ru.gortea.petter.pet.ui.state.PetUiState

internal class PetUiStateMapper : UiStateMapper<PetState, PetUiState> {

    override fun map(state: PetState): PetUiState {
        return PetUiState(
            titleRes = state.getTitleRes(),
            canDelete = state.editAvailable,
            canEdit = state.editAvailable,
            modelStatus = state.petLoadingStatus
        )
    }

    private fun PetState.getTitleRes(): Int? {
        return when {
            isCreation -> R.string.title_create
            editMode -> R.string.title_edit
            else -> null
        }
    }
}
