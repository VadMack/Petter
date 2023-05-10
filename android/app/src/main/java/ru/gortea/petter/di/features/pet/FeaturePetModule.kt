package ru.gortea.petter.di.features.pet

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.PetAnalyticsControllerImpl
import ru.gortea.petter.chat.navigation.ChatRootNode
import ru.gortea.petter.pet.analytics.PetAnalyticsController
import ru.gortea.petter.pet.navigation.PetExternalNodeProvider

@Module
class FeaturePetModule {

    @Provides
    fun providePetExternalNodeProvider(): PetExternalNodeProvider {
        return object : PetExternalNodeProvider {

            override fun chatRootNode(buildContext: BuildContext, userId: String): Node {
                return ChatRootNode(buildContext, userId)
            }
        }
    }

    @Provides
    fun providePetAnalyticsController(analytics: FirebaseAnalytics): PetAnalyticsController {
        return PetAnalyticsControllerImpl(analytics)
    }
}
