package ru.gortea.petter.home.navigation

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.navigation.PetterRouter

interface HomeExternalNodeProvider {
    fun petNode(buildContext: BuildContext, petId: String, router: PetterRouter<*>): Node
}
