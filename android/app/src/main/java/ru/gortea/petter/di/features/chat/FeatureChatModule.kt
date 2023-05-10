package ru.gortea.petter.di.features.chat

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.ChatAnalyticsControllerImpl
import ru.gortea.petter.chat.analytics.ChatAnalyticsController
import ru.gortea.petter.chat.navigation.ChatExternalNodesProvider
import ru.gortea.petter.profile.navigation.ProfileRootNode

@Module
class FeatureChatModule {

    @Provides
    fun provideChatExternalNodesProvider(): ChatExternalNodesProvider {
        return object : ChatExternalNodesProvider {

            override fun profileNode(buildContext: BuildContext, userId: String): Node {
                return ProfileRootNode(buildContext, userId)
            }
        }
    }

    @Provides
    fun provideChatAnalyticsController(analytics: FirebaseAnalytics): ChatAnalyticsController {
        return ChatAnalyticsControllerImpl(analytics)
    }
}
