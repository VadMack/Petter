package ru.gortea.petter.navigation.target

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed interface PetterRootTarget : NavTarget {
    @Parcelize
    object Authorization : PetterRootTarget

    @Parcelize
    object UserEdit : PetterRootTarget

    @Parcelize
    object Content : PetterRootTarget
}
