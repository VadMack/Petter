package ru.gortea.petter.navigation.parent

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.plugin.Destroyable
import com.bumble.appyx.navmodel.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import ru.gortea.petter.navigation.graph.NavTarget

abstract class BackStackParentNode<T : NavTarget>(
    initialTarget: T,
    buildContext: BuildContext,
    protected val backStack: BackStack<T> = BackStack(
        initialElement = initialTarget,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<T>(backStack, buildContext), Destroyable {

    protected val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun destroy() {
        coroutineScope.cancel()
    }
}
