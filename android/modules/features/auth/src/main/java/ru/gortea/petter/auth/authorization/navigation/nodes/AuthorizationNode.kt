package ru.gortea.petter.auth.authorization.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.authorization.ui.AuthorizationScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.NavTarget

class AuthorizationNode(
    buildContext: BuildContext,
    private val router: PetterRouter<NavTarget>
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        AuthorizationScreen(router = router)
    }
}
