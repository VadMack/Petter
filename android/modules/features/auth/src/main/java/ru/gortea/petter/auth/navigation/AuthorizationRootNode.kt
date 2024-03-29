package ru.gortea.petter.auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.navigation.nodes.authorization.AuthorizationNode
import ru.gortea.petter.auth.navigation.nodes.registration.RegistrationConfirmNode
import ru.gortea.petter.auth.navigation.nodes.registration.RegistrationNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget as Target

class AuthorizationRootNode(
    buildContext: BuildContext
) : BackStackParentNode<Target>(
    initialTarget = Target.Authorization,
    buildContext = buildContext
) {

    override fun resolve(navTarget: Target, buildContext: BuildContext): Node {
        return when (navTarget) {
            is Target.Authorization -> AuthorizationNode(buildContext, router)
            is Target.Registration -> resolve(navTarget, buildContext)
        }
    }

    private fun resolve(navTarget: Target.Registration, buildContext: BuildContext): Node {
        return when (navTarget) {
            is Target.Registration.RegistrationForm -> RegistrationNode(buildContext, router)
            is Target.Registration.RegistrationConfirm -> RegistrationConfirmNode(
                buildContext = buildContext,
                email = navTarget.email,
                userId = navTarget.userId,
                username = navTarget.username,
                pwd = navTarget.pwd,
                router = router
            )
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        finish()
    }
}
