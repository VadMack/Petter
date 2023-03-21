package ru.gortea.petter.auth.navigation.nodes.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.registration.registration_confirm.ui.RegistrationConfirmScreen
import ru.gortea.petter.navigation.Router

internal class RegistrationConfirmNode(
    val buildContext: BuildContext,
    val email: String,
    val userId: String,
    val username: String,
    val pwd: String,
    private val router: Router<AuthorizationNavTarget>
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        RegistrationConfirmScreen(
            email = email,
            userId = userId,
            username = username,
            pwd = pwd,
            router = router
        )
    }
}
