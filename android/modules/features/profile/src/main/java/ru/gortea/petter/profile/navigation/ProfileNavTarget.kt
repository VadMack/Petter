package ru.gortea.petter.profile.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class ProfileNavTarget : NavTarget {
    @Parcelize
    internal class Profile(val id: String, val canGoBack: Boolean = true) : ProfileNavTarget()

    @Parcelize
    internal object EditProfile : ProfileNavTarget()

    @Parcelize
    internal object AddPet : ProfileNavTarget()

    @Parcelize
    internal class OpenPet(val id: String) : ProfileNavTarget()
}
