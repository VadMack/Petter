package ru.gortea.petter.home.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget
import ru.gortea.petter.pet.list.model.PetListKeyModel

sealed class HomeNavTarget : NavTarget {

    @Parcelize
    internal object PetList : HomeNavTarget()

    @Parcelize
    internal class OpenPet(val id: String): HomeNavTarget()

    @Parcelize
    internal class Filters(val keyModel: PetListKeyModel) : HomeNavTarget()
}
