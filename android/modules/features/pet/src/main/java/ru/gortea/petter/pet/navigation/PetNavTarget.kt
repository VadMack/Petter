package ru.gortea.petter.pet.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class PetNavTarget : NavTarget {

    @Parcelize
    internal class ShowPet(val id: String) : PetNavTarget()

    @Parcelize
    internal class EditPet(val id: String?) : PetNavTarget()
}
