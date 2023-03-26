package ru.gortea.petter.pet.ui.mapper

import androidx.compose.ui.graphics.Color
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.pet.R
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.presentation.state.PetState
import ru.gortea.petter.pet.ui.state.PetFullUiModel
import ru.gortea.petter.pet.ui.state.PetUiModel
import ru.gortea.petter.pet.ui.state.PetUiState
import ru.gortea.petter.theme.Female
import ru.gortea.petter.theme.Male
import ru.gortea.petter.ui_kit.R as UiKitR

internal class PetUiStateMapper : UiStateMapper<PetState, PetUiState> {

    override fun map(state: PetState): PetUiState {
        return PetUiState(
            titleRes = state.getTitleRes(),
            canDelete = state.editAvailable,
            canEdit = state.editAvailable,
            modelStatus = state.petLoadingStatus.mapContentSync {
                val model = requireNotNull(it.model)
                PetFullUiModel(
                    photo = it.photo,
                    fields = it.fields,
                    model = PetUiModel(
                        genderIcon = model.genderIcon(),
                        genderTint = model.genderTint(),
                        name = model.name
                    ),
                    canEdit = state.editAvailable
                )
            }
        )
    }

    private fun PetFullModel.genderIcon(): Int {
        return when (gender) {
            Gender.MALE -> UiKitR.drawable.ic_male
            Gender.FEMALE -> UiKitR.drawable.ic_female
        }
    }

    private fun PetFullModel.genderTint(): Color {
        return when (gender) {
            Gender.MALE -> Male
            Gender.FEMALE -> Female
        }
    }

    private fun PetState.getTitleRes(): Int? {
        return when {
            isCreation -> R.string.title_create
            editMode -> R.string.title_edit
            else -> null
        }
    }
}
