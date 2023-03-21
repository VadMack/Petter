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
    private val coroutineScope: CoroutineScope
) : Router<T> {

    @Composable
    fun visibleChildrenAsState() = backStack.visibleChildrenAsState()

    override fun updateRoot(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.newRoot(target)
        }
    }

    override fun navigateTo(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.push(target)
        }
    }

    override fun restoreIfExists(target: T) {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.restoreIfExists(target)
        }
    }

    override fun pop() {
        coroutineScope.launch(Dispatchers.Main) {
            backStack.pop()
        }
    }
}
