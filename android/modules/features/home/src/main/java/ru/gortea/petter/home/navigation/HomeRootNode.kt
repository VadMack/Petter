package ru.gortea.petter.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import ru.gortea.petter.home.navigation.node.FiltersNode
import ru.gortea.petter.home.navigation.node.PetListNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.pet.navigation.PetRootNode

class HomeRootNode(
    buildContext: BuildContext,
    private val changeNavBarVisible: (Boolean) -> Unit
) : BackStackParentNode<HomeNavTarget>(
    initialTarget = HomeNavTarget.PetList,
    buildContext = buildContext
) {

    override fun resolve(navTarget: HomeNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is HomeNavTarget.PetList -> {
                changeNavBarVisible(true)
                PetListNode(buildContext, router)
            }
            is HomeNavTarget.OpenPet -> {
                changeNavBarVisible(false)
                PetRootNode(buildContext, navTarget.id, router)
            }
            is HomeNavTarget.Filters -> {
                changeNavBarVisible(false)
                FiltersNode(buildContext, navTarget.keyModel, router)
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackFader()
        )
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is PetRootNode -> changeNavBarVisible(true)
            is FiltersNode -> changeNavBarVisible(true)
        }
    }
}
