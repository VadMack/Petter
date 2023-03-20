package ru.gortea.petter.auth.registration.navigation

import com.bumble.appyx.navmodel.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.graph.RegistrationFlowNavTarget

class RegistrationRouter(
    backStack: BackStack<RegistrationFlowNavTarget>,
    coroutineScope: CoroutineScope
) : PetterRouter<RegistrationFlowNavTarget>(backStack, coroutineScope)
