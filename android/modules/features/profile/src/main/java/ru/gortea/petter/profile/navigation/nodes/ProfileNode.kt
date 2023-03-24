package ru.gortea.petter.profile.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.ui.ProfileScreen

internal class ProfileNode(
    buildContext: BuildContext,
    private val router: PetterRouter<ProfileNavTarget>,
    private val userId: String = "",
    private val canGoBack: Boolean = true
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        ProfileScreen(
            id = userId,
            canGoBack = canGoBack,
            router = router,
            finish = ::finish
        )
    }
}
