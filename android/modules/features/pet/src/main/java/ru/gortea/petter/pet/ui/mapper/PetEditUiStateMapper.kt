package ru.gortea.petter.pet.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.pet.R
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.presentation.state.PetState
import ru.gortea.petter.pet.presentation.state.getUnusedFields
import ru.gortea.petter.pet.ui.state.edit.PetEditFullUiModel
import ru.gortea.petter.pet.ui.state.edit.PetEditUiState
import ru.gortea.petter.ui_kit.button.ButtonState

internal class PetEditUiStateMapper : UiStateMapper<PetState, PetEditUiState> {

    override fun map(state: PetState): PetEditUiState {
        return PetEditUiState(
            titleRes = state.getTitleRes(),
            modelStatus = state.petLoadingStatus.mapContentSync {
                val model = it.model
                PetEditFullUiModel(
                    photo = it.photo,
                    fields = it.fields,
                    unusedFields = getUnusedFields(it.fields),
                    canHide = model != null,
                    isShowed = model == null || model.state == PetCardState.OPEN,
                    saveButtonState = state.getSaveButtonState()
                )
            }
        )
    }

    private fun PetState.getTitleRes(): Int? {
        return when {
            isCreation -> R.string.title_create
            editMode -> R.string.title_edit
            else -> null
        }
    }

    private fun PetState.getSaveButtonState(): ButtonState {
        return ButtonState(
            isLoading = petUpdateStatus.isLoading
        )
    }
}
