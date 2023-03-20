package ru.gortea.petter.auth.registration.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.registration.registration_confirm.ui.RegistrationConfirmScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.NavTarget

data class RegistrationConfirmNode(
    val buildContext: BuildContext,
    val email: String,
    val userId: String,
    val username: String,
    val pwd: String,
    private val router: PetterRouter<NavTarget>
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
