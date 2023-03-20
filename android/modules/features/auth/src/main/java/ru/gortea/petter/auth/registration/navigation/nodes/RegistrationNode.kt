package ru.gortea.petter.auth.registration.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.registration.registration_form.ui.RegistrationScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.NavTarget

class RegistrationNode(
    buildContext: BuildContext,
    private val router: PetterRouter<NavTarget>
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        RegistrationScreen(router)
    }
}
