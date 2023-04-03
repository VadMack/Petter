package ru.gortea.petter.pet.list.ui.state.model

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import ru.gortea.petter.ui_kit.TextModel

internal data class PetListItem(
    val id: String,
    val avatar: Uri?,
    val hasAwards: Boolean,
    val name: String,
    @DrawableRes val genderIcon: Int,
    val genderIconTint: Color,
    val breed: String,
    val age: String,
    val price: TextModel,
    val address: String?,
    val likeState: LikeState,
    val hideState: HideState
)
