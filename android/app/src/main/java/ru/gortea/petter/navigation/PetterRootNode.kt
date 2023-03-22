package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.auth.navigation.AuthorizationRootNode
import ru.gortea.petter.main.di.MainActivityComponent
import ru.gortea.petter.navigation.parent.BackStackParentNode
import ru.gortea.petter.navigation.target.PetterRootTarget
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
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
        val authObservable = getComponent<MainActivityComponent>().authObservable
        val isAuthorized by authObservable.isAuthorized().collectAsState()
        if (isAuthorized) {
            router.updateRoot(PetterRootTarget.Content)
        } else {
            router.updateRoot(PetterRootTarget.Authorization)
        }
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is AuthorizationRootNode -> backStack.newRoot(PetterRootTarget.Content)
        }
    }
}
