package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.authorization.navigation.nodes.AuthorizationNode
import ru.gortea.petter.auth.registration.navigation.nodes.FillAccountNode
import ru.gortea.petter.auth.registration.navigation.nodes.RegistrationConfirmNode
import ru.gortea.petter.auth.registration.navigation.nodes.RegistrationNode
import ru.gortea.petter.navigation.graph.AuthorizationNavTarget
import ru.gortea.petter.navigation.graph.NavTarget
import ru.gortea.petter.navigation.graph.RegistrationFlowNavTarget
import ru.gortea.petter.navigation.parent.BackStackParentNode

class PetterRootNode(
    buildContext: BuildContext
) : BackStackParentNode<NavTarget>(
    initialTarget = AuthorizationNavTarget.Authorization,
    buildContext = buildContext
) {
    private val router = PetterRouter(backStack, coroutineScope)

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            // todo move to separate resolvers
            is RegistrationFlowNavTarget.RegistrationForm -> RegistrationNode(buildContext, router)
            is RegistrationFlowNavTarget.RegistrationConfirm -> RegistrationConfirmNode(
                buildContext = buildContext,
                email = navTarget.email,
                userId = navTarget.userId,
                username = navTarget.username,
                pwd = navTarget.pwd,
                router = router
            )
            is RegistrationFlowNavTarget.FillAccount -> FillAccountNode(buildContext)
            is AuthorizationNavTarget.Authorization -> AuthorizationNode(buildContext, router)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}
