package ru.gortea.petter.home.filters.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.home.filters.presentation.constants.GenderFilterOption
import ru.gortea.petter.home.filters.presentation.constants.SpeciesFilterOption
import ru.gortea.petter.home.filters.presentation.filters.EnumFilter
import ru.gortea.petter.home.filters.presentation.filters.Filter
import ru.gortea.petter.home.filters.presentation.filters.RangeFilter
import ru.gortea.petter.home.filters.presentation.filters.SimpleFieldFilter
import ru.gortea.petter.home.navigation.commands.HomeNavCommand
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.home.filters.presentation.FiltersState as State
import ru.gortea.petter.home.filters.presentation.FiltersUiEvent as UiEvent
import ru.gortea.petter.pet.R as PetR

@Suppress("UNCHECKED_CAST")
internal class FiltersReducer(
    private val router: Router<*>
) : Reducer<State, UiEvent, Nothing>() {

    override fun MessageBuilder<State, Nothing>.reduce(event: UiEvent) {
        when (event) {
            is UiEvent.ScreenOpened -> Unit
            is UiEvent.Accept -> {
                router.sendCommand(HomeNavCommand.AcceptKeyModel(state.toKeyModel()))
                router.pop()
            }
            is UiEvent.GoBack -> router.pop()
            is UiEvent.UpdateFilter -> state { updateFilter(event.filter) }
        }
    }

    private fun State.updateFilter(filter: Filter): State {
        return when (filter) {
            is EnumFilter<*> -> updateEnumFilter(filter)
            is RangeFilter -> updateRangeFilter(filter)
            is SimpleFieldFilter -> updateSimpleFieldFilter(filter)
        }
    }

    private fun State.updateEnumFilter(filter: EnumFilter<*>): State {
        return when (filter.titleRes) {
            PetR.string.species -> copy(speciesFilter = filter as EnumFilter<SpeciesFilterOption>)
            PetR.string.gender -> copy(genderFilter = filter as EnumFilter<GenderFilterOption>)
            else -> error("Unknown enum filter")
        }
    }

    private fun State.updateRangeFilter(filter: RangeFilter): State {
        return when (filter.titleRes) {
            PetR.string.price -> copy(priceFilter = filter.validated())
            else -> error("Unknown range filter")
        }
    }

    private fun State.updateSimpleFieldFilter(filter: SimpleFieldFilter): State {
        return when (filter.titleRes) {
            PetR.string.breed -> copy(breedFilter = filter)
            else -> error("Unknown field filter")
        }
    }
}
