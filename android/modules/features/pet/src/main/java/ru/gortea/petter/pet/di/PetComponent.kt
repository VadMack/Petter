package ru.gortea.petter.pet.di

import ru.gortea.petter.formatters.DateFormatter
import ru.gortea.petter.pet.data.CreatePetRepository
import ru.gortea.petter.pet.data.DeletePetRepository
import ru.gortea.petter.pet.data.GetPetRepository
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.data.UpdatePetRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository

interface PetComponent {
    val petLikeRepository: PetLikeRepository
    val createPetRepository: CreatePetRepository
    val updatePetRepository: UpdatePetRepository
    val deletePetRepository: DeletePetRepository
    val getPetRepository: GetPetRepository
    val userRepository: CurrentUserRepository
    val dateFormatter: DateFormatter
}
