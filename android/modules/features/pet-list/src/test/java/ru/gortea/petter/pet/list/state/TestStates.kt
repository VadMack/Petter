package ru.gortea.petter.pet.list.state

import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.pet.list.R
import ru.gortea.petter.pet.list.ui.state.PetListUiState
import ru.gortea.petter.pet.list.ui.state.model.HideState
import ru.gortea.petter.pet.list.ui.state.model.LikeState
import ru.gortea.petter.pet.list.ui.state.model.PetListItem
import ru.gortea.petter.theme.Female
import ru.gortea.petter.ui_kit.TextModel
import ru.gortea.petter.ui_kit.R as UiKitR

internal object TestStates {
    val liked = PetListUiState(
        offset = 2,
        dataState = PagingDataState.Paged.Content(
            content = listOf(
                PetListItem(
                    id = "id",
                    avatar = null,
                    hasAwards = false,
                    name = "",
                    genderIcon = UiKitR.drawable.ic_female,
                    genderIconTint = Female,
                    breed = "",
                    age = "01.01.2023",
                    price = TextModel(R.string.zero_price),
                    likeState = LikeState.LIKED,
                    hideState = HideState.NOT_AVAILABLE,
                    address = null
                )
            )
        )
    )

    val disliked = PetListUiState(
        offset = 2,
        dataState = PagingDataState.Paged.Content(
            content = listOf(
                PetListItem(
                    id = "id",
                    avatar = null,
                    hasAwards = false,
                    name = "",
                    genderIcon = UiKitR.drawable.ic_female,
                    genderIconTint = Female,
                    breed = "",
                    age = "01.01.2023",
                    price = TextModel(R.string.zero_price),
                    likeState = LikeState.UNLIKED,
                    hideState = HideState.NOT_AVAILABLE,
                    address = null
                )
            )
        )
    )

    val pageLoading = PetListUiState(
        offset = 2,
        dataState = PagingDataState.Paged.Loading(
            content = listOf(
                PetListItem(
                    id = "id",
                    avatar = null,
                    hasAwards = false,
                    name = "",
                    genderIcon = UiKitR.drawable.ic_female,
                    genderIconTint = Female,
                    breed = "",
                    age = "01.01.2023",
                    price = TextModel(R.string.zero_price),
                    likeState = LikeState.UNLIKED,
                    hideState = HideState.NOT_AVAILABLE,
                    address = null
                )
            )
        )
    )

    val myPet = PetListUiState(
        offset = 2,
        dataState = PagingDataState.Paged.Loading(
            content = listOf(
                PetListItem(
                    id = "id",
                    avatar = null,
                    hasAwards = false,
                    name = "",
                    genderIcon = UiKitR.drawable.ic_female,
                    genderIconTint = Female,
                    breed = "",
                    age = "01.01.2023",
                    price = TextModel(R.string.zero_price),
                    likeState = LikeState.NOT_AVAILABLE,
                    hideState = HideState.OPENED,
                    address = null
                )
            )
        )
    )
}
