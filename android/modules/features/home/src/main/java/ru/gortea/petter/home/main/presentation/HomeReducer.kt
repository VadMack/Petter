package ru.gortea.petter.home.main.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.home.main.presentation.HomeState as State
import ru.gortea.petter.home.main.presentation.HomeUiEvent as UiEvent

internal class HomeReducer(
    private val router: Router<HomeNavTarget>
) : Reducer<State, UiEvent, Nothing>() {

    override fun MessageBuilder<State, Nothing>.reduce(event: UiEvent) {
        when(event) {
            is UiEvent.OpenPet -> router.navigateTo(HomeNavTarget.OpenPet(event.id))
            is UiEvent.AcceptFilters -> state { copy(keyModel = event.keyModel) }
            is UiEvent.OpenFilters -> router.navigateTo(HomeNavTarget.Filters(state.keyModel))
        }
    }
}
