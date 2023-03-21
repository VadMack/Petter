package ru.gortea.petter.root.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class BottomNavigationTarget : NavTarget {
    @Parcelize
    internal object Main : BottomNavigationTarget()

    @Parcelize
    internal object Chats : BottomNavigationTarget()

    @Parcelize
    internal object Profile : BottomNavigationTarget()
}
