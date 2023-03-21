package ru.gortea.petter.root.navigation

import androidx.compose.runtime.Composable
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.Router

class BottomNavigationRouter(
    private val router: PetterRouter<BottomNavigationTarget>
) : Router<BottomNavigationTarget> by router {

    @Composable
    fun currentNodeAsState() = router.visibleChildrenAsState()
 }
