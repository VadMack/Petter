package ru.gortea.petter.pet.list.di

import ru.gortea.petter.formatters.BirthDateFormatter
import ru.gortea.petter.formatters.SimpleDateFormatter
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.list.data.PetApiStub
import ru.gortea.petter.pet.list.data.PetListApiStub
import ru.gortea.petter.pet.list.data.PetListRepository
import ru.gortea.petter.pet.list.data.UserDaoStub
import ru.gortea.petter.pet.list.data.api.PetListApiService
import ru.gortea.petter.profile.data.local.CurrentUserRepository

internal class TestComponent(private val myId: String = "myId") : PetListComponent {

    override val currentUserRepository: CurrentUserRepository
        get() = CurrentUserRepository(UserDaoStub(myId))

    override val petListRepository: PetListRepository
        get() = PetListRepository(
            api = PetListApiService(PetListApiStub),
            userRepository = currentUserRepository
        )
    override val petLikeRepository: PetLikeRepository
        get() = PetLikeRepository(PetApiStub)
    override val dateFormatter: BirthDateFormatter
        get() = SimpleDateFormatter()
}
