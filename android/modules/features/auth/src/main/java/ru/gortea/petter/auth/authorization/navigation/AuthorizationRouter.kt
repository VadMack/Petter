package ru.gortea.petter.auth.authorization.navigation

import com.bumble.appyx.navmodel.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.AuthorizationNavTarget

class AuthorizationRouter(
    backStack: BackStack<AuthorizationNavTarget>,
    coroutineScope: CoroutineScope
) : PetterRouter<AuthorizationNavTarget>(backStack, coroutineScope)
