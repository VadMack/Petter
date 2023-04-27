package ru.gortea.petter.chat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.chat.di.ChatComponent
import ru.gortea.petter.chat.navigation.nodes.ChatNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode

class ChatRootNode(
    buildContext: BuildContext,
    private val userId: String = ""
) : BackStackParentNode<ChatNavTarget>(
    initialTarget = ChatNavTarget.Chat,
    buildContext = buildContext
) {

    private val nodesProvider by lazy {
        provideComponent<ChatComponent>().chatNodesProvider
    }

    override fun resolve(navTarget: ChatNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is ChatNavTarget.Chat -> ChatNode(buildContext, userId, router)
            is ChatNavTarget.Profile -> nodesProvider.profileNode(buildContext, userId)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}
