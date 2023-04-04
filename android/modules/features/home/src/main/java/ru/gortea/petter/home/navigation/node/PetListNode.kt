package ru.gortea.petter.home.navigation.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.home.ui.HomeScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode

internal class PetListNode(
    buildContext: BuildContext,
    private val router: PetterRouter<HomeNavTarget>
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        HomeScreen(router)
    }
}
