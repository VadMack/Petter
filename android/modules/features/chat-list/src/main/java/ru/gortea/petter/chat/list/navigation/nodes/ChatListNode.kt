package ru.gortea.petter.chat.list.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import ru.gortea.petter.chat.list.navigation.ChatListNavTarget
import ru.gortea.petter.chat.list.ui.ChatListScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode

internal class ChatListNode(
    buildContext: BuildContext,
    private val router: PetterRouter<ChatListNavTarget>
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        ChatListScreen(router = router)
    }
}
