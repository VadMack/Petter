package ru.gortea.petter.home.filters.presentation

import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.arch.android.util.textIfNotBlank
import ru.gortea.petter.home.filters.presentation.constants.GenderFilterOption
import ru.gortea.petter.home.filters.presentation.constants.SpeciesFilterOption
import ru.gortea.petter.home.filters.presentation.constants.toGender
import ru.gortea.petter.home.filters.presentation.constants.toSpecies
import ru.gortea.petter.home.filters.presentation.filters.EnumFilter
import ru.gortea.petter.home.filters.presentation.filters.RangeFilter
import ru.gortea.petter.home.filters.presentation.filters.SimpleFieldFilter
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.R as PetR

internal data class FiltersState private constructor(
    val keyModel: PetListKeyModel,
    val priceFilter: RangeFilter,
    val speciesFilter: EnumFilter<SpeciesFilterOption>,
    val breedFilter: SimpleFieldFilter,
    val genderFilter: EnumFilter<GenderFilterOption>
) {
    companion object {
        fun fromKeyModel(model: PetListKeyModel): FiltersState {
            return FiltersState(
                keyModel = model,
                priceFilter = RangeFilter(
                    titleRes = PetR.string.price,
                    minLimit = FieldState(text = model.minPrice?.toString() ?: ""),
                    maxLimit = FieldState(text = model.maxPrice?.toString() ?: "")
                ),
                speciesFilter = EnumFilter(
                    titleRes = PetR.string.species,
                    items = SpeciesFilterOption.values().toList(),
                    selected = model.species?.toFilterOption() ?: SpeciesFilterOption.UNKNOWN
                ),
                breedFilter = SimpleFieldFilter(
                    titleRes = PetR.string.breed,
                    field = FieldState(model.breed ?: "")
                ),
                genderFilter = EnumFilter(
                    titleRes = PetR.string.gender,
                    items = GenderFilterOption.values().toList(),
                    selected = model.gender?.toFilterOption() ?: GenderFilterOption.UNKNOWN
                )
            )
        }

        private fun Species.toFilterOption(): SpeciesFilterOption {
            return when (this) {
                Species.CAT -> SpeciesFilterOption.CAT
                Species.DOG -> SpeciesFilterOption.DOG
            }
        }

        private fun Gender.toFilterOption(): GenderFilterOption {
            return when (this) {
                Gender.MALE -> GenderFilterOption.MALE
                Gender.FEMALE -> GenderFilterOption.FEMALE
            }
        }
    }
}

internal fun FiltersState.toKeyModel(): PetListKeyModel {
    return keyModel.copy(
        minPrice = priceFilter.minLimit.textIfNotBlank()?.toInt(),
        maxPrice = priceFilter.maxLimit.textIfNotBlank()?.toInt(),
        species = speciesFilter.selected.toSpecies(),
        breed = breedFilter.field.textIfNotBlank(),
        gender = genderFilter.selected.toGender()
    )
}
