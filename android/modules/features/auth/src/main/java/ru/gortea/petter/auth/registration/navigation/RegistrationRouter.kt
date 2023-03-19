package ru.gortea.petter.auth.registration.navigation

import com.bumble.appyx.navmodel.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import ru.gortea.petter.navigation.AppyxRouter
import ru.gortea.petter.navigation.graph.RegistrationFlowNavTarget

class RegistrationRouter(
    backStack: BackStack<RegistrationFlowNavTarget>,
    coroutineScope: CoroutineScope = GlobalScope
) : AppyxRouter<RegistrationFlowNavTarget>(backStack, coroutineScope)
