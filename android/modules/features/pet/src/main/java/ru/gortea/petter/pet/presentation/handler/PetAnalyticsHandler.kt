package ru.gortea.petter.pet.presentation.handler

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.pet.analytics.PetAnalyticsController
import ru.gortea.petter.pet.presentation.PetEvent
import ru.gortea.petter.pet.presentation.state.PetState

internal class PetAnalyticsHandler(
    private val controller: PetAnalyticsController
) : AnalyticsHandler<PetState,PetEvent> {

    override fun logEvent(state: PetState, event: PetEvent) {
        when(event) {
            is PetEvent.IsMyPet -> controller.opened(event.isMyPet)
            is PetEvent.InitApi -> if (state.editMode) controller.editOpened()
            else -> Unit
        }
    }
}
