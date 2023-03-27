package ru.gortea.petter.pet.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.navigation.PetNavTarget
import ru.gortea.petter.pet.ui.PetScreen

internal class PetNode(
    buildContext: BuildContext,
    private val router: PetterRouter<PetNavTarget>,
    private val petId: String
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        PetScreen(petId, router)
    }
}
