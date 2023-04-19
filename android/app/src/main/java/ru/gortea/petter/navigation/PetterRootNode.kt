package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.auth.navigation.AuthorizationRootNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.navigation.target.PetterRootTarget
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.edit.navigation.ProfileEditNode
import ru.gortea.petter.root.navigation.node.ContentRootParentNode

class PetterRootNode(
    authObservable: AuthObservable,
    userRepo: CurrentUserRepository,
    buildContext: BuildContext
) : BackStackParentNode<PetterRootTarget>(
    initialTarget = PetterRootTarget.Authorization,
    buildContext = buildContext
) {
    private val targetController = PetterRootTargetController(
        authObservable,
        userRepo,
        coroutineScope
    )

    init {
        coroutineScope.launch {
            targetController.get()
                .onEach { router.updateRoot(it) }
                .collect()
        }
    }

    override fun resolve(navTarget: PetterRootTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            is PetterRootTarget.Authorization -> AuthorizationRootNode(buildContext)
            is PetterRootTarget.Content -> ContentRootParentNode(buildContext)
            is PetterRootTarget.UserEdit -> ProfileEditNode(
                buildContext = buildContext,
                isProfileCreate = true,
                router = router
            )
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        when (child) {
            is AuthorizationRootNode -> router.updateRoot(PetterRootTarget.Content)
            is ProfileEditNode -> router.updateRoot(PetterRootTarget.Content)
        }
    }
}
