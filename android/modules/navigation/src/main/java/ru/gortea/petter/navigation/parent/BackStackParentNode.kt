package ru.gortea.petter.navigation.parent

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.plugin.Destroyable
import com.bumble.appyx.navmodel.backstack.BackStack
import ru.gortea.petter.navigation.NavTarget
import ru.gortea.petter.navigation.PetterRouter

abstract class BackStackParentNode<T : NavTarget>(
    initialTarget: T,
    buildContext: BuildContext,
    protected val backStack: BackStack<T> = BackStack(
        initialElement = initialTarget,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<T>(backStack, buildContext), Destroyable {

    protected val router = PetterRouter(backStack, (parent as? BackStackParentNode<*>)?.backStack)

    override fun destroy() {
        finish()
    }
}
