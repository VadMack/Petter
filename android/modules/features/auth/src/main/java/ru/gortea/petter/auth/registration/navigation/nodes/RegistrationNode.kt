package ru.gortea.petter.auth.registration.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.registration.navigation.RegistrationRouter
import ru.gortea.petter.auth.registration.registration_form.ui.RegistrationScreen

class RegistrationNode(
    buildContext: BuildContext,
    private val router: RegistrationRouter
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        RegistrationScreen(router)
    }
}
