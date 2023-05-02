package ru.gortea.petter.pet.list.ui.mapper

import android.net.Uri
import androidx.compose.ui.graphics.Color
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.paging.model.mapContent
import ru.gortea.petter.formatters.BirthDateFormatter
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.list.R
import ru.gortea.petter.pet.list.data.model.PetListItemModel
import ru.gortea.petter.pet.list.presentation.PetListState
import ru.gortea.petter.pet.list.ui.state.PetListUiState
import ru.gortea.petter.pet.list.ui.state.model.HideState
import ru.gortea.petter.pet.list.ui.state.model.LikeState
import ru.gortea.petter.pet.list.ui.state.model.PetListItem
import ru.gortea.petter.theme.Female
import ru.gortea.petter.theme.Male
import ru.gortea.petter.ui_kit.TextModel
import ru.gortea.petter.ui_kit.R as UiKitR

internal class PetListUiStateMapper(
    private val formatter: BirthDateFormatter
) : UiStateMapper<PetListState, PetListUiState> {

    override fun map(state: PetListState): PetListUiState {
        return PetListUiState(
            dataState = state.dataState.mapContent { model ->
                PetListItem(
                    id = model.id,
                    avatar = model.photoPath?.let(Uri::parse),
                    hasAwards = model.hasAchievements,
                    name = model.name,
                    genderIcon = model.gender.genderIcon(),
                    genderIconTint = model.gender.genderTint(),
                    breed = model.breed,
                    age = formatter.format(model.birthDate),
                    price = if (model.price <= 0) TextModel(R.string.zero_price) else TextModel(
                        model.price.toString()
                    ),
                    likeState = model.likeState(state.currentUserId),
                    hideState = model.hideState(state.currentUserId),
                    address = model.address?.toString()
                )
            },
            offset = state.pageSize * 2
        )
    }

    private fun PetListItemModel.likeState(currentUser: String?): LikeState {
        return when {
            ownerId == currentUser -> LikeState.NOT_AVAILABLE
            liked -> LikeState.LIKED
            else -> LikeState.UNLIKED
        }
    }

    private fun PetListItemModel.hideState(currentUser: String?): HideState {
        return when {
            ownerId != currentUser -> HideState.NOT_AVAILABLE
            state == PetCardState.OPEN -> HideState.OPENED
            else -> HideState.HIDDEN
        }
    }

    private fun Gender.genderIcon(): Int {
        return when (this) {
            Gender.MALE -> UiKitR.drawable.ic_male
            Gender.FEMALE -> UiKitR.drawable.ic_female
        }
    }

    private fun Gender.genderTint(): Color {
        return when (this) {
            Gender.MALE -> Male
            Gender.FEMALE -> Female
        }
    }
}
