package ru.gortea.petter.chat.list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.chat.list.di.ChatListComponent
import ru.gortea.petter.chat.list.navigation.nodes.ChatListNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode

class ChatListRootNode(
    buildContext: BuildContext,
    private val changeNavBarVisible: (Boolean) -> Unit
) : BackStackParentNode<ChatListNavTarget>(
    initialTarget = ChatListNavTarget.ChatList,
    buildContext = buildContext
) {

    private val nodesProvider by lazy {
        provideComponent<ChatListComponent>().chatListNodesProvider
    }

    override fun resolve(navTarget: ChatListNavTarget, buildContext: BuildContext): Node {
        return when(navTarget) {
            is ChatListNavTarget.ChatList -> ChatListNode(buildContext, router)
            is ChatListNavTarget.Chat -> {
                changeNavBarVisible(false)
                nodesProvider.chat(buildContext, navTarget.userId)
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        changeNavBarVisible(true)
    }
}
