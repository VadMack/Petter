package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.navigation.AuthorizationRootNode
import ru.gortea.petter.navigation.parent.BackStackParentNode
import ru.gortea.petter.navigation.target.PetterRootTarget

class PetterRootNode(
    buildContext: BuildContext
) : BackStackParentNode<PetterRootTarget>(
    initialTarget = PetterRootTarget.Authorization,
    buildContext = buildContext
) {
    override fun resolve(navTarget: PetterRootTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is PetterRootTarget.Authorization -> AuthorizationRootNode(buildContext)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}
