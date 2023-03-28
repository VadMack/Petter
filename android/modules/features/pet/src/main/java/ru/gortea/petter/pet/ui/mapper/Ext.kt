package ru.gortea.petter.pet.ui.mapper

import androidx.compose.ui.graphics.Color
import ru.gortea.petter.pet.data.model.constants.AchievementLevel

fun AchievementLevel.iconTint(): Color {
    return when (this) {
        AchievementLevel.WINNER -> Color(0xFFF8EC91)
        AchievementLevel.MEDALIST -> Color(0xFFC0C0C0)
        AchievementLevel.PARTICIPANT -> Color.Transparent
    }
}
