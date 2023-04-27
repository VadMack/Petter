package ru.gortea.petter.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.edit.navigation.ProfileEditNode
import ru.gortea.petter.profile.navigation.nodes.ProfileNode

class ProfileRootNode(
    buildContext: BuildContext,
    id: String = "",
    canGoBack: Boolean = true,
    private val changeNavBarVisible: (Boolean) -> Unit = {}
) : BackStackParentNode<ProfileNavTarget>(
    initialTarget = ProfileNavTarget.Profile(id, canGoBack),
    buildContext = buildContext
) {

    private val nodeProvider by lazy {
        provideComponent<ProfileComponent>().profileNodesProvider
    }

    override fun resolve(navTarget: ProfileNavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is ProfileNavTarget.EditProfile -> {
                changeNavBarVisible(false)
                ProfileEditNode(buildContext, router)
            }

            is ProfileNavTarget.Profile -> {
                changeNavBarVisible(true)
                ProfileNode(buildContext, router, navTarget.id, navTarget.canGoBack)
            }

            is ProfileNavTarget.AddPet -> {
                changeNavBarVisible(false)
                nodeProvider.petNode(buildContext, null, router)
            }

            is ProfileNavTarget.OpenPet -> {
                changeNavBarVisible(false)
                nodeProvider.petNode(buildContext, navTarget.id, router)
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
            is ProfileNode -> finish()
            else -> changeNavBarVisible(true)
        }
    }
}
