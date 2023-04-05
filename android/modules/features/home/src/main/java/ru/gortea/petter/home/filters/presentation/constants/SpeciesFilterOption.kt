package ru.gortea.petter.home.filters.presentation.constants

import androidx.annotation.StringRes
import ru.gortea.petter.home.R
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.R as PetR

internal enum class SpeciesFilterOption(@StringRes override val titleRes: Int) : TitleHolder {
    UNKNOWN(R.string.filter_unknown),
    DOG(PetR.string.species_dog),
    CAT(PetR.string.species_cat)
}

internal fun SpeciesFilterOption.toSpecies(): Species? {
    return when(this) {
        SpeciesFilterOption.DOG -> Species.DOG
        SpeciesFilterOption.CAT -> Species.CAT
        SpeciesFilterOption.UNKNOWN -> null
    }
}
