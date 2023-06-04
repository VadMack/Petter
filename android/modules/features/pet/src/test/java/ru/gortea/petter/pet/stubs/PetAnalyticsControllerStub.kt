package ru.gortea.petter.pet.stubs

import ru.gortea.petter.pet.analytics.PetAnalyticsController

internal class PetAnalyticsControllerStub : PetAnalyticsController {
    override fun opened(isMine: Boolean) = Unit

    override fun editOpened() = Unit
}
