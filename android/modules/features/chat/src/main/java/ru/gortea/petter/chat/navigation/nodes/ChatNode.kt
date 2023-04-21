package ru.gortea.petter.chat.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import ru.gortea.petter.chat.navigation.ChatNavTarget
import ru.gortea.petter.chat.ui.ChatScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode

internal class ChatNode(
    buildContext: BuildContext,
    private val userId: String,
    private val router: PetterRouter<ChatNavTarget>
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        ChatScreen(userId, router)
    }
}
