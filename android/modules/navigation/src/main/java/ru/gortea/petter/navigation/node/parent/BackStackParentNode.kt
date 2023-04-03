package ru.gortea.petter.navigation.node.parent

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.plugin.Destroyable
import com.bumble.appyx.navmodel.backstack.BackStack
import ru.gortea.petter.navigation.NavTarget
import ru.gortea.petter.navigation.PetterRouter

abstract class BackStackParentNode<T : NavTarget>(
    initialTarget: T,
    buildContext: BuildContext,
    parentRouter: PetterRouter<*>? = null,
    protected val backStack: BackStack<T> = BackStack(
        initialElement = initialTarget,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<T>(backStack, buildContext), Destroyable {

    protected val router = PetterRouter(
        backStack = backStack,
        parentBackStack = (parent as? BackStackParentNode<*>)?.backStack,
        commandsController = parentRouter
    )

    override fun destroy() {
        finish()
        router.destroy()
    }
}
