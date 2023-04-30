package ru.gortea.petter.di.features.chat_list

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dagger.Module
import dagger.Provides
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
}
