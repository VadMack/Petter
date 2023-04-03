package ru.gortea.petter.pet.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.navmodel.backstack.operation.Pop
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode
import ru.gortea.petter.pet.navigation.PetNavTarget
import ru.gortea.petter.pet.ui.PetScreen

internal class PetNode(
    buildContext: BuildContext,
    private val router: PetterRouter<PetNavTarget>,
    private val petId: String
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val visibleChild by router.visibleChildrenAsState()
        val child = visibleChild.firstOrNull()
        val needReload = child?.key?.navTarget is PetNavTarget.ShowPet && child.operation is Pop

        PetScreen(petId, router, needReload)
    }
}
