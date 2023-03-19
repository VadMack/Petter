package ru.gortea.petter.navigation

import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AppyxRouter<in T : Any>(
    private val backStack: BackStack<T>,
    private val coroutineScope: CoroutineScope
) {

    fun updateRoot(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.newRoot(target)
        }
    }

    fun navigateTo(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.push(target)
        }
    }

    fun singleTop(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.singleTop(target)
        }
    }

    fun pop() {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.pop()
        }
    }
}
