package ru.gortea.petter.home.presentation

import ru.gortea.petter.arch.Reducer
import ru.gortea.petter.arch.model.MessageBuilder
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.home.presentation.HomeState as State
import ru.gortea.petter.home.presentation.HomeUiEvent as UiEvent

internal class HomeReducer(
    private val router: PetterRouter<HomeNavTarget>
) : Reducer<State, UiEvent, Nothing, Nothing>() {

    override fun MessageBuilder<State, Nothing, Nothing>.reduce(event: UiEvent) {
        when(event) {
            is UiEvent.OpenPet -> router.navigateTo(HomeNavTarget.OpenPet(event.id))
        }
    }
}
