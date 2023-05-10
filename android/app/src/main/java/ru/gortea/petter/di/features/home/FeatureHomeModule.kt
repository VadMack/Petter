package ru.gortea.petter.di.features.home

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.HomeAnalyticsControllerImpl
import ru.gortea.petter.home.analytics.HomeAnalyticsController
import ru.gortea.petter.home.navigation.HomeExternalNodeProvider
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.navigation.PetRootNode

@Module
class FeatureHomeModule {

    @Provides
    fun provideHomeExternalNodeProvider(): HomeExternalNodeProvider {
        return object : HomeExternalNodeProvider {
            override fun petNode(
                buildContext: BuildContext,
                petId: String,
                router: PetterRouter<*>
            ): Node {
                return PetRootNode(buildContext, petId, router)
            }

        }
    }

    @Provides
    fun provideHomeAnalyticsController(analytics: FirebaseAnalytics): HomeAnalyticsController {
        return HomeAnalyticsControllerImpl(analytics)
    }
}
