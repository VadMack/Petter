package ru.gortea.petter.pet.ui.state

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

internal data class PetUiModel(
    @DrawableRes val genderIcon: Int,
    val genderTint: Color,
    val name: String
)
