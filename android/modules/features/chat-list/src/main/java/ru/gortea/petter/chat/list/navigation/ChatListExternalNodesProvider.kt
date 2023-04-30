package ru.gortea.petter.chat.list.navigation

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

interface ChatListExternalNodesProvider {
    fun chat(buildContext: BuildContext, userId: String): Node
}
