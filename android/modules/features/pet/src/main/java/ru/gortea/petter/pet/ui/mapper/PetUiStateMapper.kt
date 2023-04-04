package ru.gortea.petter.pet.ui.mapper

import androidx.compose.ui.graphics.Color
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.data.model.isLoading
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.presentation.state.PetState
import ru.gortea.petter.pet.ui.state.showing.PetFullUiModel
import ru.gortea.petter.pet.ui.state.showing.PetLikeStatus
import ru.gortea.petter.pet.ui.state.showing.PetUiModel
import ru.gortea.petter.pet.ui.state.showing.PetUiState
import ru.gortea.petter.theme.Female
import ru.gortea.petter.theme.Male
import ru.gortea.petter.ui_kit.R as UiKitR

internal class PetUiStateMapper : UiStateMapper<PetState, PetUiState> {

    override fun map(state: PetState): PetUiState {
        return PetUiState(
            canDelete = state.isMine,
            canEdit = state.isMine,
            likeStatus = state.toLikeStatus(),
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
                    canEdit = state.isMine
                )
            },
            isDeleteLoading = state.petDeleteStatus.run { isLoading || isContent }
        )
    }

    private fun PetState.toLikeStatus(): PetLikeStatus {
        if (petLoadingStatus !is DataState.Content) return PetLikeStatus.NOT_AVAILABLE

        val model = petLoadingStatus.content.model ?: return PetLikeStatus.NOT_AVAILABLE

        return when {
            isMine -> PetLikeStatus.NOT_AVAILABLE
            model.liked -> PetLikeStatus.LIKED
            else -> PetLikeStatus.UNLIKED
        }
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
}
