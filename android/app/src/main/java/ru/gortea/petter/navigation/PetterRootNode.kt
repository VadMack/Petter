package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.auth.navigation.AuthorizationRootNode
import ru.gortea.petter.main.di.MainActivityComponent
import ru.gortea.petter.navigation.parent.BackStackParentNode
import ru.gortea.petter.navigation.target.PetterRootTarget
import ru.gortea.petter.profile.edit.navigation.ProfileEditNode
import ru.gortea.petter.root.navigation.node.ContentRootParentNode

class PetterRootNode(
    buildContext: BuildContext
) : BackStackParentNode<PetterRootTarget>(
    initialTarget = PetterRootTarget.Authorization,
    buildContext = buildContext
) {

    private val router = PetterRouter(backStack, coroutineScope)

    override fun resolve(navTarget: PetterRootTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is PetterRootTarget.Authorization -> AuthorizationRootNode(buildContext)
            is PetterRootTarget.Content -> ContentRootParentNode(buildContext)
            is PetterRootTarget.UserEdit -> ProfileEditNode(buildContext, isProfileCreate = true)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)

        val component = getComponent<MainActivityComponent>()
        val authObservable = remember { component.authObservable }
        val userLocalRepository = remember { component.userLocalRepository }

        val isAuthorized by authObservable.isAuthorized().collectAsState()
        val isEmpty by userLocalRepository.isEmpty().collectAsState(false)

        when {
            isAuthorized && isEmpty -> router.updateRoot(PetterRootTarget.UserEdit)
            isAuthorized -> router.updateRoot(PetterRootTarget.Content)
            else -> router.updateRoot(PetterRootTarget.Authorization)
        }
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is AuthorizationRootNode -> router.updateRoot(PetterRootTarget.Content)
            is ProfileEditNode -> router.updateRoot(PetterRootTarget.Content)
        }
    }
}
