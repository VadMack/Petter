package ru.gortea.petter.pet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.activeElement
import ru.gortea.petter.chat.navigation.ChatRootNode
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.pet.navigation.nodes.PetEditNode
import ru.gortea.petter.pet.navigation.nodes.PetNode
 
class PetRootNode(
    buildContext: BuildContext,
    petId: String?,
    parentRouter: PetterRouter<*>? = null
) : BackStackParentNode<PetNavTarget>(
    initialTarget = if (petId == null) PetNavTarget.EditPet(null) else PetNavTarget.ShowPet(petId),
    buildContext = buildContext,
    parentRouter = parentRouter
) {

    override fun resolve(navTarget: PetNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is PetNavTarget.EditPet -> PetEditNode(buildContext, router, navTarget.id)
            is PetNavTarget.ShowPet -> PetNode(buildContext, router, navTarget.id)
            is PetNavTarget.OpenChat -> ChatRootNode(buildContext, navTarget.userId)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is PetNode -> finish()
            is PetEditNode -> if (backStack.activeElement !is PetNavTarget.ShowPet) finish()
        }
    }
}
