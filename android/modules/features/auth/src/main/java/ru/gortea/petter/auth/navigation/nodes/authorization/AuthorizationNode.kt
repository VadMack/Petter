package ru.gortea.petter.auth.navigation.nodes.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.authorization.ui.AuthorizationScreen
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.navigation.Router

internal class AuthorizationNode(
    buildContext: BuildContext,
    private val router: Router<AuthorizationNavTarget>
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        AuthorizationScreen(router = router) { finish() }
    }
}
