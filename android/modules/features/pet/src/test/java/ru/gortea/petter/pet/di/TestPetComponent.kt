package ru.gortea.petter.pet.di

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.formatters.BirthDateFormatter
import ru.gortea.petter.formatters.SimpleDateFormatter
import ru.gortea.petter.pet.analytics.PetAnalyticsController
import ru.gortea.petter.pet.data.CreatePetRepository
import ru.gortea.petter.pet.data.DeletePetRepository
import ru.gortea.petter.pet.data.GetPetRepository
import ru.gortea.petter.pet.data.PetLikeRepository
import ru.gortea.petter.pet.data.UpdatePetRepository
import ru.gortea.petter.pet.navigation.PetExternalNodeProvider
import ru.gortea.petter.pet.stubs.ContentFileConverterStub
import ru.gortea.petter.pet.stubs.PetAnalyticsControllerStub
import ru.gortea.petter.pet.stubs.PetApiStub
import ru.gortea.petter.pet.stubs.UserDaoStub
import ru.gortea.petter.profile.data.local.CurrentUserRepository

internal class TestPetComponent : PetComponent {
    override val petLikeRepository: PetLikeRepository
        get() = PetLikeRepository(PetApiStub())
    override val createPetRepository: CreatePetRepository
        get() = CreatePetRepository(PetApiStub(), ContentFileConverterStub())
    override val updatePetRepository: UpdatePetRepository
        get() = UpdatePetRepository(PetApiStub(), ContentFileConverterStub())
    override val deletePetRepository: DeletePetRepository
        get() = DeletePetRepository(PetApiStub())
    override val getPetRepository: GetPetRepository
        get() = GetPetRepository(PetApiStub())
    override val userRepository: CurrentUserRepository
        get() = CurrentUserRepository(UserDaoStub())

    override val dateFormatter: BirthDateFormatter = SimpleDateFormatter()
    override val simpleDateFormatter: SimpleDateFormatter = SimpleDateFormatter()

    override val petNodeProvider: PetExternalNodeProvider
        get() = object : PetExternalNodeProvider {

            override fun chatRootNode(buildContext: BuildContext, userId: String): Node {
                throw Exception()
            }
        }

    override val petAnalyticsController: PetAnalyticsController
        get() = PetAnalyticsControllerStub()
}
