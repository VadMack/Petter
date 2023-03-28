package ru.gortea.petter.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import ru.gortea.petter.navigation.parent.BackStackParentNode
import ru.gortea.petter.pet.navigation.PetRootNode
import ru.gortea.petter.profile.edit.navigation.ProfileEditNode
import ru.gortea.petter.profile.navigation.nodes.ProfileNode

class ProfileRootNode(
    id: String = "",
    canGoBack: Boolean = true,
    buildContext: BuildContext,
    private val changeNavBarVisible: (Boolean) -> Unit
) : BackStackParentNode<ProfileNavTarget>(
    initialTarget = ProfileNavTarget.Profile(id, canGoBack),
    buildContext = buildContext
) {

    override fun resolve(navTarget: ProfileNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is ProfileNavTarget.EditProfile -> {
                changeNavBarVisible(false)
                ProfileEditNode(buildContext)
            }
            is ProfileNavTarget.Profile -> {
                changeNavBarVisible(true)
                ProfileNode(buildContext, router, navTarget.id, navTarget.canGoBack)
            }
            is ProfileNavTarget.AddPet -> {
                changeNavBarVisible(false)
                PetRootNode(buildContext, null)
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackFader()
        )
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is ProfileEditNode -> {
                changeNavBarVisible(true)
                router.pop()
            }
            is PetRootNode -> changeNavBarVisible(true)
            is ProfileNode -> finish()
        }
    }
}
