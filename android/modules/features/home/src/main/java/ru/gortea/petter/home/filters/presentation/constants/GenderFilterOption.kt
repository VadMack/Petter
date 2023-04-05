package ru.gortea.petter.home.filters.presentation.constants

import androidx.annotation.StringRes
import ru.gortea.petter.home.R
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.R as PetR

internal enum class GenderFilterOption(@StringRes override val titleRes: Int) : TitleHolder {
    UNKNOWN(R.string.filter_unknown),
    MALE(PetR.string.gender_male),
    FEMALE(PetR.string.gender_female)
}

internal fun GenderFilterOption.toGender(): Gender? {
    return when (this) {
        GenderFilterOption.MALE -> Gender.MALE
        GenderFilterOption.FEMALE -> Gender.FEMALE
        GenderFilterOption.UNKNOWN -> null
    }
}
