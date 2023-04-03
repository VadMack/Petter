package ru.gortea.petter.navigation

import androidx.compose.runtime.Composable
import com.bumble.appyx.core.composable.visibleChildrenAsState
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.bumble.appyx.navmodel.backstack.operation.push
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gortea.petter.navigation.operation.restoreIfExists

class PetterRouter<T : NavTarget>(
    private val backStack: BackStack<T>,
    private val parentBackStack: BackStack<*>?,
    private val commandsController: CommandController? = null
) : Router<T> {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    override val commands = MutableStateFlow<NavCommand>(NavCommand.Empty)

    init {
        if (commandsController != null) {
            coroutineScope.launch {
                commands
                    .onEach { commandsController.sendCommand(it) }
                    .collect()
            }
        }
    }

    override fun setCommandsConsumer(consumer: (NavCommand) -> Unit) {
        coroutineScope.launch {
            commands.collect(consumer)
        }
    }

    override fun sendCommand(command: NavCommand) {
        coroutineScope.launch {
            commands.emit(command)
        }
    }

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

    override fun pop() {
        CoroutineScope(Dispatchers.Main).launch {
            val isCurrentEnabled = backStack.onBackPressedCallback.isEnabled
            if (isCurrentEnabled) {
                backStack.onBackPressedCallback.handleOnBackPressed()
            } else {
                parentBackStack?.onBackPressedCallback?.handleOnBackPressed()
            }
        }
    }

    internal fun destroy() {
        coroutineScope.cancel()
    }
}
