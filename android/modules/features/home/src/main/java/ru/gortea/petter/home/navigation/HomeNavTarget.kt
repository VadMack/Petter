package ru.gortea.petter.home.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class HomeNavTarget : NavTarget {

    @Parcelize
    internal object PetList : HomeNavTarget()

    @Parcelize
    internal class OpenPet(val id: String): HomeNavTarget()
}
