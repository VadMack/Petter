package ru.gortea.petter.auth.authorization.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.authorization.navigation.AuthorizationRouter
import ru.gortea.petter.auth.authorization.ui.AuthorizationScreen

class AuthorizationNode(
    buildContext: BuildContext,
    private val router: AuthorizationRouter
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        AuthorizationScreen(router = router)
    }
}
