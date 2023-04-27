package ru.gortea.petter.di.features.profile

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dagger.Module
import dagger.Provides
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.navigation.PetRootNode
import ru.gortea.petter.profile.navigation.ProfileExternalNodesProvider

@Module
class FeatureProfileModule {

    @Provides
    fun provideProfileExternalNodesProvider(): ProfileExternalNodesProvider {
        return object : ProfileExternalNodesProvider {
            override fun petNode(
                buildContext: BuildContext,
                petId: String?,
                parentRouter: PetterRouter<*>?
            ): Node {
                return PetRootNode(buildContext, petId, parentRouter)
            }

        }
    }
}
