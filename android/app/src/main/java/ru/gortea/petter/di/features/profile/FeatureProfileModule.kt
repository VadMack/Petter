package ru.gortea.petter.di.features.profile

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.ProfileAnalyticsControllerImpl
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.navigation.PetRootNode
import ru.gortea.petter.profile.analytics.ProfileAnalyticsController
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

    @Provides
    fun provideProfileAnalyticsController(
        analytics: FirebaseAnalytics
    ): ProfileAnalyticsController {
        return ProfileAnalyticsControllerImpl(analytics)
    }
}
