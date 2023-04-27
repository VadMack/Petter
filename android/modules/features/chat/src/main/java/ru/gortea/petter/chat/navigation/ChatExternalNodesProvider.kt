package ru.gortea.petter.chat.navigation

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

interface ChatExternalNodesProvider {

    fun profileNode(buildContext: BuildContext, userId: String): Node
}
