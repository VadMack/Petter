package ru.gortea.petter.pet.list.di

import ru.gortea.petter.formatters.DateFormatter
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.profile.data.local.UserLocalRepository

interface PetListComponent {
    val petListRepository: PetListRepository
    val petLikeRepository: PetLikeRepository
    val userLocalRepository: UserLocalRepository
    val dateFormatter: DateFormatter
}
