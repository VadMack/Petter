package ru.gortea.petter.home.main.presentation

import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.list.model.PetListKeyModel

internal data class HomeState(
    val keyModel: PetListKeyModel = PetListKeyModel(
        excludeMe = true,
        state = PetCardState.OPEN
    )
)
