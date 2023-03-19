package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.registration.navigation.RegistrationRouter
import ru.gortea.petter.auth.registration.navigation.nodes.FillAccountNode
import ru.gortea.petter.auth.registration.navigation.nodes.RegistrationConfirmNode
import ru.gortea.petter.auth.registration.navigation.nodes.RegistrationNode
import ru.gortea.petter.navigation.graph.RegistrationFlowNavTarget
import ru.gortea.petter.navigation.parent.BackStackParentNode

class PetterParentNode(
    buildContext: BuildContext
) : BackStackParentNode<RegistrationFlowNavTarget>(
    initialTarget = RegistrationFlowNavTarget.RegistrationForm,
    buildContext = buildContext
) {

    private val router = RegistrationRouter(backStack)

    override fun resolve(navTarget: RegistrationFlowNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            RegistrationFlowNavTarget.RegistrationForm -> RegistrationNode(buildContext, router)
            is RegistrationFlowNavTarget.RegistrationConfirm -> RegistrationConfirmNode(
                buildContext = buildContext,
                email = navTarget.email,
                userId = navTarget.userId,
                username = navTarget.username,
                pwd = navTarget.pwd,
                router = router
            )
            RegistrationFlowNavTarget.FillAccount -> FillAccountNode(buildContext)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}
