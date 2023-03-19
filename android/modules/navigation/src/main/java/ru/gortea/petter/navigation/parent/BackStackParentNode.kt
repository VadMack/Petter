package ru.gortea.petter.navigation.parent

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack

abstract class BackStackParentNode<T : Any>(
    initialTarget: T,
    buildContext: BuildContext,
    protected val backStack: BackStack<T> = BackStack(
        initialElement = initialTarget,
        savedStateMap = buildContext.savedStateMap
    )
) : ParentNode<T>(backStack, buildContext)
