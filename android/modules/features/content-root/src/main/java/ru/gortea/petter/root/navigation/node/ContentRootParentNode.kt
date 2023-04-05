package ru.gortea.petter.root.navigation.node

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import kotlinx.coroutines.flow.MutableStateFlow
import ru.gortea.petter.home.navigation.HomeRootNode
import ru.gortea.petter.navigation.node.parent.BackStackParentNode
import ru.gortea.petter.profile.navigation.ProfileRootNode
import ru.gortea.petter.root.BottomNavigationContainer
import ru.gortea.petter.root.navigation.BottomNavigationRouter
import ru.gortea.petter.root.navigation.BottomNavigationTarget as Target

class ContentRootParentNode(
    buildContext: BuildContext
) : BackStackParentNode<Target>(
    initialTarget = Target.Main,
    buildContext = buildContext
) {
    private val showBottomBar = MutableStateFlow(true)
    private val bottomRouter = BottomNavigationRouter(router)

    override fun resolve(navTarget: Target, buildContext: BuildContext): Node {
        return when (navTarget) {
            is Target.Main -> HomeRootNode(buildContext, showBottomBar::tryEmit)
            is Target.Chats -> StubNode(buildContext, 2)
            is Target.Profile -> ProfileRootNode(
                canGoBack = false,
                buildContext = buildContext,
                changeNavBarVisible = showBottomBar::tryEmit
            )
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        val showNavBar by showBottomBar.collectAsState()
        Scaffold(
            content = {
                Children(
                    navModel = backStack,
                    modifier = Modifier.padding(it),
                    transitionHandler = rememberBackstackSlider(
                        transitionSpec = { tween() }
                    )
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = showNavBar,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    BottomNavigationContainer(bottomRouter)
                }
            }
        )
    }
}
