package ru.gortea.petter.pet.analytics

interface PetAnalyticsController {
    fun opened(isMine: Boolean)
    fun editOpened()
}
