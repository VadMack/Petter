package ru.gortea.petter.pet.list.di

import ru.gortea.petter.formatters.DateFormatter
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.profile.data.local.UserLocalRepository

interface PetListComponent {
    val petListRepository: PetListRepository
    val userLocalRepository: UserLocalRepository
    val dateFormatter: DateFormatter
}
