package ru.gortea.petter.di.features.chat_list

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import ru.gortea.petter.analytics.ChatListAnalyticsControllerImpl
import ru.gortea.petter.chat.list.analytics.ChatListAnalyticsController
import ru.gortea.petter.chat.list.navigation.ChatListExternalNodesProvider
import ru.gortea.petter.chat.navigation.ChatRootNode

@Module
class FeatureChatListModule {

    @Provides
    fun provideChatListExternalNodesProvider(): ChatListExternalNodesProvider {
        return object : ChatListExternalNodesProvider {

            override fun chat(buildContext: BuildContext, userId: String): Node {
                return ChatRootNode(buildContext, userId)
            }
        }
    }

    @Provides
    fun provideChatListAnalyticsController(
        analytics: FirebaseAnalytics
    ): ChatListAnalyticsController {
        return ChatListAnalyticsControllerImpl(analytics)
    }
}
