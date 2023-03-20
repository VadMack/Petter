package ru.gortea.petter.auth.authorization.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.authorization.navigation.AuthorizationRouter
import ru.gortea.petter.auth.registration.navigation.nodes.RegistrationParentNode
import ru.gortea.petter.navigation.graph.AuthorizationNavTarget
import ru.gortea.petter.navigation.parent.BackStackParentNode

class AuthorizationParentNode(
    buildContext: BuildContext
) : BackStackParentNode<AuthorizationNavTarget>(
    initialTarget = AuthorizationNavTarget.Authorization,
    buildContext = buildContext
) {
    private val router = AuthorizationRouter(backStack, coroutineScope)

    override fun resolve(navTarget: AuthorizationNavTarget, buildContext: BuildContext): Node {
        return when(navTarget) {
            is AuthorizationNavTarget.Authorization -> AuthorizationNode(buildContext, router)
            is AuthorizationNavTarget.Registration -> RegistrationParentNode(buildContext)
        }
    }
    
    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}
