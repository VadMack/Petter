package ru.gortea.petter.profile.navigation

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.navigation.PetterRouter

interface ProfileExternalNodesProvider {

    fun petNode(
        buildContext: BuildContext,
        petId: String?,
        parentRouter: PetterRouter<*>? = null
    ): Node
}
