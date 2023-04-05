package ru.gortea.petter.home.filters.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.home.filters.presentation.FiltersState
import ru.gortea.petter.home.filters.ui.state.FiltersUiState

internal class FiltersUiStateMapper : UiStateMapper<FiltersState, FiltersUiState> {
    override fun map(state: FiltersState): FiltersUiState {
        return FiltersUiState(
            filters = listOf(
                state.priceFilter,
                state.speciesFilter,
                state.breedFilter,
                state.genderFilter
            )
        )
    }
}
