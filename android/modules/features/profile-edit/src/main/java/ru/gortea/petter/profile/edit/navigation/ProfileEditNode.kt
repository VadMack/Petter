package ru.gortea.petter.profile.edit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.profile.edit.ui.ProfileEditScreen

class ProfileEditNode(
    buildContext: BuildContext,
    private val router: PetterRouter<*>,
    private val isProfileCreate: Boolean = false
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        ProfileEditScreen(isProfileCreate, router, ::finish)
    }
}

