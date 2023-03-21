package ru.gortea.petter.auth.navigation.nodes.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.registration.registration_form.ui.RegistrationScreen
import ru.gortea.petter.navigation.PetterRouter

internal class RegistrationNode(
    buildContext: BuildContext,
    private val router: PetterRouter<AuthorizationNavTarget>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        RegistrationScreen(router)
    }
}
