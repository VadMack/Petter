package ru.gortea.petter.pet.navigation

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

interface PetExternalNodeProvider {
    fun chatRootNode(buildContext: BuildContext, userId: String): Node
}
