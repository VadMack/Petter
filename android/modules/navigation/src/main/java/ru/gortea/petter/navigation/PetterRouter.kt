package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import com.bumble.appyx.core.composable.visibleChildrenAsState
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gortea.petter.navigation.operation.restoreIfExists

class PetterRouter<T : NavTarget>(
    private val backStack: BackStack<T>,
    private val parentBackStack: BackStack<*>?
) : Router<T> {

    @Composable
    fun visibleChildrenAsState() = backStack.visibleChildrenAsState()

    override fun updateRoot(target: T) {
        CoroutineScope(Dispatchers.Main).launch {
            backStack.newRoot(target)
        }
    }

    override fun navigateTo(target: T) {
        CoroutineScope(Dispatchers.Main).launch {
            backStack.push(target)
        }
    }

    override fun restoreIfExists(target: T) {
        CoroutineScope(Dispatchers.Main).launch {
            backStack.restoreIfExists(target)
        }
    }

    override fun pop(current: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (current) {
                backStack.pop()
            } else {
                parentBackStack?.pop() ?: backStack.pop()
            }
        }
    }
}
