package ru.gortea.petter.pet.list.di

import ru.gortea.petter.formatters.BirthDateFormatter
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository

interface PetListComponent {
    val petListRepository: PetListRepository
    val petLikeRepository: PetLikeRepository
    val currentUserRepository: CurrentUserRepository
    val dateFormatter: BirthDateFormatter
}
